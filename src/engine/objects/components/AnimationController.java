package engine.objects.components;

import engine.Animation;
import engine.Animation.*;
import engine.objects.Component;
import engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Component that controls animations.
 *
 * @author Bailey
 */

public class AnimationController extends Component {
    /**
     * Represents a parameter.
     */
    public class Parameter {
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
    public record Transition(Animation state1, Animation state2, Parameter[] params, boolean[] values) {}

    private final List<Parameter> parameters = new ArrayList<>();
    private final List<Transition> transitions = new ArrayList<>();
    private final List<Animation> animations = new ArrayList<>();

    private Animation current;

    /**
     * The constructor.
     * @param object The parent object.
     * @param defaultState The default animation to be playing (idle animation).
     */
    public AnimationController(GameObject object, Animation defaultState) {
        super(object);
        animations.add(defaultState);
        current = defaultState;
    }

    /**
     * To be run every frame, checks for transitions, etc.
     */
    @Override
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
                current.play(parent);
            }
        });
        if (current.loop && current.status == Status.STOPPED) {
            current.play(parent);
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
     * @param name The name to reference the animation by.
     * @param frames The list of frames in the animation.
     */
    public void addAnim(String name, boolean loop, Animation.Frame[] frames) {
        this.animations.add(new Animation(name, loop, frames));
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
            if (parameter.name.equals(name)) parameter.value = value;
        });
    }

    /**
     * Allows you to see the value of a parameter.
     * @param name The name of the parameter.
     * @return The value of the parameter. If nothing is found, it returns false.
     */
    public boolean checkParam(String name) {
        for (Parameter parameter : parameters)
            if (parameter.name.equals(name)) return parameter.value;
        return false;
    }
    /**
     * Adds a transition between two animations/states.
     * @param state1 The state starting state.
     * @param state2 The finishing state.
     * @param localParams The dependent variable, the parameters.
     * @param value The value that the parameter needs to be inorder for a transition to occur.
     */
    public void addTransition(String state1, String state2, String[] localParams, boolean[] value) {
        Animation anim1 = null;
        Animation anim2 = null;
        List<Parameter> params = new ArrayList<>();
        for (Animation animation : animations)
            if (animation.name.equals(state1)) anim1 = animation;
            else if (animation.name.equals(state2)) anim2 = animation;
        for (Parameter parameterObj : parameters)
        	for (String parameterStr : localParams)
	            if (parameterObj.name.equals(parameterStr)) params.add(parameterObj);

        transitions.add(new Transition(anim1, anim2, params.toArray(new Parameter[0]), value));
    }
}
