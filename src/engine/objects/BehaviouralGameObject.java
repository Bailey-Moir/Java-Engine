package engine.objects;

import java.util.ArrayList;
import java.util.Collection;

import engine.GlobalStorage;
import engine.Script;
import engine.io.Window;
import engine.maths.Vector;

/**
 * Represents an advanced with potential for scripts and components game object.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class BehaviouralGameObject extends GameObject implements Script {
	public Collection<Component> components = new ArrayList<>();

	/**
	 * Constructor.
	 * @param position The starting position of the game object.
	 * @param size The starting size of the game object.
	 * @param image The image to be displayed on the game object.
	 */
	public BehaviouralGameObject(Vector position, Vector size, Vector color, int layer, String image) {
		super(position, size, color, layer, image);

		GlobalStorage.scripts.add(this);
	}

	/**
	 * Updates all of the components of the object.
	 */
	public void updateComponents() {
		components.forEach(Component::update);
	}
}
