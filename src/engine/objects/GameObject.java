package engine.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import engine.Script;
import engine.maths.Vector2;
import engine.maths.Vector4;
import engine.objects.components.SpriteRenderer;
import engine.objects.components.Transform;

/**
 * Represents a game object.
 *
 * @author Bailey
 */

public abstract class GameObject implements Script {
	public static final List<GameObject> all = new ArrayList<>();
    public static final Collection<Script> scripts = new ArrayList<>();
    
	public final Collection<Component> components = new ArrayList<>();
	
    public final Transform transform;
    public final SpriteRenderer spriteRenderer;
    
    /**
     * Constructor.
     * @param position The starting position of the game object.
     * @param size The starting size of the game object.
     * @param image The image to be displayed on the game object.
     */
    public GameObject(Vector2 position, Vector2 size, Vector4 color, int layer, String image) {
        this.transform = new Transform(this);
        this.spriteRenderer = new SpriteRenderer(color, image, layer, this);

        this.transform.position = position;
        this.transform.size = size;
        
        all.add(this);        
        scripts.add(this);
    }

    /**
	 * Updates all the components of the object.
	 */
	public void updateComponents() {
		components.forEach(Component::update);
	}
}