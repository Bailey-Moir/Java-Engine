package engine.components;

import engine.GameObject;
import engine.maths.Vector;

/**
 * Handles the physics of an obj.
 * 
 * @author Bailey
 */

public class Rigidbody {
	private GameObject obj;
	
	public float gravityModifier = 0.5f;
	
	public Vector net, velocity;
	
	public boolean isGravity;
	
	/**
	 * The constructor.
	 * @param object The obj that the component belongs to.
	 */
	public Rigidbody(GameObject object) {		
		this.obj = object;
		net = Vector.square(0, 2);
		velocity = new Vector(new float[]{0, 0});
		this.isGravity = true;
	}
	
	/**
	 * Acts upon the member variables, e.g. <code>net</code>, or <code>velocity</code>. 
	 * </br></br>
	 * <b>To be run every frame.<b>
	 */
	public void update() {		
		if (isGravity) {
			addForce(new Vector(new float[]{0, -gravityModifier}));
		}
		
		velocity = net.times((float) obj.spriteRenderer.getWindow().time.deltaTime).times(0.005f); //F = a
		
		obj.transform.position = obj.transform.position.plus(velocity);
		net = net.minus(velocity.times(2)); //Two is the modifier of friction.
		
		if (net.dimensions.get(0) <= 0.1 && net.dimensions.get(0) >= -0.1) {
			net.dimensions.set(0, 0f);
		}
		if (net.dimensions.get(1) <= 0.1 && net.dimensions.get(1) >= -0.1) {
			net.dimensions.set(1, 0f);
		}
		if (velocity.dimensions.get(0) <= 0.1 && velocity.dimensions.get(0) >= -0.1) {
			velocity.dimensions.set(0, 0f);
		}
		if (velocity.dimensions.get(1) <= 0.1 && velocity.dimensions.get(1) >= -0.1) {
			velocity.dimensions.set(1, 0f);
		}
			
	}
		
	/**
	 * Adds a force to the obj.
	 * @param force The force to add to the net force of the obj.
	 */
	public void addForce(Vector force) {
		net = net.plus(force);
	}

	/**
	 * Makes the obj stop falling.
	 */
	public void stopFalling() {
		if (net.dimensions.get(1) < 0) net.dimensions.set(1, 0f);
	}
}
