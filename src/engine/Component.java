package engine;

/**
 * The abstract class for a component of an object.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class Component {
    protected GameObject object;

    public Component(GameObject object) {
        this.object = object;
    }
}
