package engine.objects;

import java.util.ArrayList;
import java.util.Collection;

import engine.Script;
import engine.maths.Vector2;
import engine.maths.Vector4;

/**
 * Represents an advanced with potential for scripts and components game object.
 *
 * @author Bailey
 */

public abstract class BehaviouralGameObject extends GameObject implements Script {
	public Collection<Component> components = new ArrayList<>();

	/**
	 * Constructor.
	 * @param position The starting position of the game object.
	 * @param size The starting size of the game object.
	 * @param image The image to be displayed on the game object.
	 */
	public BehaviouralGameObject(Vector2 position, Vector2 size, Vector4 color, int layer, String image) {
		super(position, size, color, layer, image);
		
		GameObject.scripts.add(this);
	}

	/**
	 * Updates all the components of the object.
	 */
	public void updateComponents() {
		components.forEach(Component::update);
	}
}
