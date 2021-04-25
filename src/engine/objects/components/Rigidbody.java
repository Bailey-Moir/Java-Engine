package engine.objects.components;
import engine.objects.Component;
import engine.objects.GameObject;
import engine.maths.Vector;

/**
 * Handles the physics of an object.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Rigidbody extends Component {
	public float gravityModifier = 0.5f;
	
	public Vector net = Vector.square(0, 2), velocity = new Vector(0, 0);
	public float mass = 1;
	
	public boolean isGravity = false;

	/**
	 * The constructor.
	 * @param parent The obj that the component belongs to.
	 */
	public Rigidbody(GameObject parent) {
		init(parent);
	}

	/**
	 * Acts upon the member variables, e.g. <code>net</code>, or <code>velocity</code>. 
	 * </br></br>
	 * <b>To be run every frame.<b>
	 */
	@Override
	public void update() {
		if (isGravity) {
			addForce(new Vector(0, -gravityModifier*mass));
		}

		// TODO uncomment this.
		velocity = net/*.times((float) object.spriteRenderer.getWindow().time.deltaTime)*/.times(0.075f).times(1/mass); //F = a
		
		parent.transform.position = parent.transform.position.plus(velocity);
		net = net.times(0.8f); //Two is the modifier of friction. TODO Use delta time here

		limZero(net);
		limZero(velocity);

	}

	private void limZero(Vector vec) {
		if (vec.getAxis(0) <= 0.1 && vec.getAxis(0) >= -0.1) {
			vec.setAxis(0, 0f);
		}
		if (vec.getAxis(1) <= 0.1 && vec.getAxis(1) >= -0.1) {
			vec.setAxis(1, 0f);
		}
	}

	/**
	 * Adds a force to the object.
	 * @param force The force to add to the net force of the object.
	 */
	public void addForce(Vector force) {
		net = net.plus(force);
	}
}
