package engine.objects.components;
import engine.maths.Vector2;
import engine.objects.Component;
import engine.objects.GameObject;

/**
 * Handles the physics of an object.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Rigidbody extends Component {
	public float gravityModifier = 0.5f;
	
	public Vector2 net = Vector2.zero(), velocity = Vector2.zero();
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
			addForce(new Vector2(0, -gravityModifier*mass));
		}

		// TODO uncomment this.
		velocity = net/*.times((float) object.spriteRenderer.getWindow().time.deltaTime)*/._times(0.075f)._times(1/mass); //F = a
		
		parent.transform.position = parent.transform.position._plus(velocity);
		net = net._times(0.8f); //Two is the modifier of friction. TODO Use delta time here

		limZero(net);
		limZero(velocity);

	}

	private void limZero(Vector2 vec) {
		if (Math.abs(vec.x) <= 0.1) {
			vec.x = 0;
		}
		if (Math.abs(vec.y) <= 0.1) {
			vec.y = 0;
		}
	}

	/**
	 * Adds a force to the object.
	 * @param force The force to add to the net force of the object.
	 */
	public void addForce(Vector2 force) {
		net = net._plus(force);
	}
}
