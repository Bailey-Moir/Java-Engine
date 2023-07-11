package engine.objects.components;

import engine.maths.Vector2;
import engine.objects.Component;
import engine.objects.GameObject;

/**
 * Size, positions, movement, rotation, etc.
 *
 * @author Bailey
 */
public class Transform extends Component {
    public float rotation = 0;

    public Vector2 position, size;
    
    public Transform(GameObject object) { super(object); }
    
    /**
     * Moves the object, including delta time.
     * @param delta How much it is going to change by.
     */
    public void translate(Vector2 delta) {
        position = position._plus(delta._times((float) (1 / parent.spriteRenderer.getWindow().time.deltaTime)));
    }
}
