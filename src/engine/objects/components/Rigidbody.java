package engine.objects.components;
import engine.objects.GameObject;
import engine.maths.Vector;

/**
 * Handles the physics of an object.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Rigidbody {
	public GameObject object;

	public float gravityModifier = 0.5f;
	
	public Vector net, velocity;
	public float mass;
	
	public boolean isGravity;
	
	/**
	 * The constructor.
	 * @param object The obj that the component belongs to.
	 */
	public Rigidbody(GameObject object) {
		this.object = object;
		net = Vector.square(0, 2);
		velocity = new Vector(new float[]{0, 0});
		mass = 1;
		isGravity = false;
	}
	
	/**
	 * Acts upon the member variables, e.g. <code>net</code>, or <code>velocity</code>. 
	 * </br></br>
	 * <b>To be run every frame.<b>
	 */
	public void update() {		
		if (isGravity) {
			addForce(new Vector(new float[]{0, -gravityModifier*mass}));
		}

		velocity = net.times((float) object.spriteRenderer.getWindow().time.deltaTime).times(0.005f).times(1/mass); //F = a
		
		object.transform.position = object.transform.position.plus(velocity);
		net = net.minus(velocity.times(2)); //Two is the modifier of friction.

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

	/**
	 * Makes the obj stop falling.
	 */
	public void stopFalling() {
		if (net.getAxis(1) < 0) net.setAxis(1, 0f);
	}
}
