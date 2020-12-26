package engine.components;

import engine.GameObject;
import engine.Component;
import engine.maths.Vector2f;

/**
 * Handles the physics of an obj.
 * 
 * @author Bailey
 */

public class Rigidbody extends Component{
	private GameObject obj;
	
	public float gravityModifier = 0.5f;
	
	public Vector2f net, velocity;
	
	public boolean isGravity;
	
	/**
	 * The constructor.
	 * @param obj The obj that the component belongs to.
	 */
	public Rigidbody(GameObject object) {
                super(object);		
		this.obj = object;
		net = new Vector2f(0, 0);
		this.isGravity = true;
	}
	
	/**
	 * Acts upon the member variables, e.g. <code>net</code>, or <code>velocity</code>. 
	 * </br></br>
	 * <b>To be run every frame.<b>
	 */
	public void update() {		
		if (isGravity) {
			addForce(new Vector2f(0, -gravityModifier));
		}
		
		velocity = net.times((float) obj.spriteRenderer.getWindow().time.deltaTime).times(0.005f); //F = a
		
		obj.transform.position = obj.transform.position.plus(velocity);
		net = net.minus(velocity.times(2)); //Two is the modifier of friction.
		
		if (net.x <= 0.1 && net.x >= -0.1) {
			net.x = 0;
		}
		if (net.y <= 0.1 && net.y >= -0.1) {
			net.y = 0;
		}
		if (velocity.x <= 0.1 && velocity.x >= -0.1) {
			velocity.x = 0;
		}
		if (velocity.y <= 0.1 && velocity.y >= -0.1) {
			velocity.y = 0;
		}
			
	}
		
	/**
	 * Adds a force to the obj.
	 * @param force The force to add to the net force of the obj.
	 */
	public void addForce(Vector2f force) {
		net = net.plus(force);
	}

	/**
	 * Makes the obj stop falling.
	 */
	public void stopFalling() {
		if (net.y < 0) net.y = 0;
	}
}
