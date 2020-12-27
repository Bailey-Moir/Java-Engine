package engine.components;

import engine.Animation;
import engine.Animation.*;
import engine.Component;
import engine.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        public final Parameter param;
        public final boolean value;

        public Transition(Animation state1, Animation state2, Parameter param, boolean value) {
            this.state1 = state1;
            this.state2 = state2;
            this.param = param;
            this.value = value;
        }
    }

    private List<Parameter> parameters = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private List<Animation> animations = new ArrayList<>();

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
        transitions.forEach((transition -> {
           if (transition.state1 == current && transition.value == transition.param.value) {
                current = transition.state2;
                current.play();
           }
        }));
        if (current.loop == true && current.status == Status.STOPPED) {
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
            if (parameter.name == name) {
                parameter.value = value;
            }
        });
    }

    /**
     * Adds a transition between two animations/states.
     * @param state1 The state starting state.
     * @param state2 The finishing state.
     * @param param The dependent variable, parameter.
     * @param value The value that the parameter needs to be inorder for a transition to occur.
     */
    public void addTransition(String state1, String state2, String param, boolean value) {
        AtomicReference<Animation> anim1 = new AtomicReference<>();
        AtomicReference<Animation> anim2 = new AtomicReference<>();
        AtomicReference<Parameter> paramClass = new AtomicReference<>();
        animations.forEach((animation) -> {
            if (animation.name == state1) {
                anim1.set(animation);
            } else if (animation.name == state2) {
                anim2.set(animation);
            }
        });
        parameters.forEach((parameter) -> {
            if (parameter.name == param) {
                paramClass.set(parameter);
            }
        });

        //Accounting for typos.
        if (anim1.get() == null) {
            new NullPointerException("First animation does not exist.").printStackTrace();
        }
        if (anim2.get() == null) {
            new NullPointerException("Second animation does not exist.").printStackTrace();
        }
        if (paramClass.get() == null) {
            new NullPointerException("Parameter does not exist.").printStackTrace();
        }

        transitions.add(new Transition(anim1.get(), anim2.get(), paramClass.get(), value));
    }
}
