package engine.objects.components;

import engine.Animation;
import engine.Animation.*;
import engine.objects.Component;
import engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * Component that controls animations.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class AnimationController extends Component {
    /**
     * Represents a parameter.
     */
    public static class Parameter {
        public final String name;
        public boolean value;

        public Parameter(String name) {
            this.name = name;
            this.value = false;
        }
    }
    /**
     * Represents a transition.
     */
    public static class Transition {
        public final Animation state1, state2;
        public final Parameter[] params;
        public final boolean[] values;

        public Transition(Animation state1, Animation state2, Parameter[] params, boolean[] values) {
            this.state1 = state1;
            this.state2 = state2;
            this.params = params;
            this.values = values;
        }
    }

    private final List<Parameter> parameters = new ArrayList<>();
    private final List<Transition> transitions = new ArrayList<>();
    private final List<Animation> animations = new ArrayList<>();

    private Animation current;

    /**
     * The constructor.
     * @param object The parent object.
     */
    public AnimationController(GameObject object, Animation defaultState) {
        super(object);
        animations.add(defaultState);
        current = defaultState;
    }

    /**
     * To be run every frame, checks for transitions, etc.
     */
    public void update() {
        transitions.forEach(transition -> {
            boolean paramsPassed = true;
            for (int i = 0; i < transition.params.length; i++) {
                if (transition.values[i] != transition.params[i].value) {
                    paramsPassed = false;
                    break;
                }
            }
            if (transition.state1 == current && paramsPassed) {
                current.stop();
                current = transition.state2;
                current.play();
            }
        });
        if (current.loop && current.status == Status.STOPPED) {
            current.play();
        }
    }

    /**
     * Adds an animation to the controller.
     * @param anim The animation.
     */
    public void addAnim(Animation anim) {
        this.animations.add(anim);
    }

    /**
     * Adds an animation to the controller.
     * @param speed The speed that the animation plays at, default 1.
     * @param name The name to reference the animation by.
     * @param object The objeect that the animation acts on.
     * @param frames The list of frames in the animation.
     */
    public void addAnim(float speed, String name, GameObject object, boolean loop, Animation.Frame[] frames) {
        this.animations.add(new Animation(speed, name, object, loop, frames));
    }

    /**
     * Adds a parameter to the controller.
     * @param name The name of the parameter.
     */
    public void addParam(String name) {
        parameters.add(new Parameter(name));
    }

    /**
     * Sets the value of a parameter that has been previously created.
     * @param name The name of the parameter.
     * @param value The value to set the parameter to.
     */
    public void setParam(String name, boolean value) {
        parameters.forEach((parameter) -> {
            if (parameter.name.equals(name)) {
                parameter.value = value;
            }
        });
    }

    /**
     * Allows you to see the value of a parameter.
     * @param name The name of the parameter.
     * @return The value of the parameter.
     */
    public boolean checkParam(String name) {
        AtomicReference<Boolean> toReturn = new AtomicReference<>(false);
        parameters.forEach((parameter) -> {
            if (parameter.name.equals(name)) {
                toReturn.set(parameter.value);
            }
        });
        return toReturn.get();
    }
    /**
     * Adds a transition between two animations/states.
     * @param state1 The state starting state.
     * @param state2 The finishing state.
     * @param localParams The dependent variable, the parameters.
     * @param value The value that the parameter needs to be inorder for a transition to occur.
     */
    public void addTransition(String state1, String state2, String[] localParams, boolean[] value) {
        AtomicReference<Animation> anim1 = new AtomicReference<>(null);
        AtomicReference<Animation> anim2 = new AtomicReference<>(null);
        AtomicReference<List<Parameter>> params = new AtomicReference<>(new ArrayList<>());
        animations.forEach(animation -> {
            if (animation.name.equals(state1)) {
                anim1.set(animation);
            } else if (animation.name.equals(state2)) {
                anim2.set(animation);
            }
        });
        parameters.forEach(parameterObj -> Stream.of(localParams).forEach(parameterStr -> {
                if (parameterObj.name.equals(parameterStr)) {
                    params.get().add(parameterObj);
                }
        }));

        transitions.add(new Transition(anim1.get(), anim2.get(), params.get().toArray(new Parameter[0]), value));
    }
}
