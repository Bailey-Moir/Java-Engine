package engine.objects;

/**
 * The interface that all components extend off of.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class Component {
    public GameObject parent;
    
    public void update() {}

    public final void init(BehaviouralGameObject parent) {
        this.parent = parent;
        parent.components.add(this);
    }
}
