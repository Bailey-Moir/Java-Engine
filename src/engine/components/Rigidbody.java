package engine.components;

import engine.GameObject;
import engine.maths.Vector2f;

public class Rigidbody {
	private GameObject object;
	
	public float gravityModifier = 0.5f;
	
	public Vector2f net, velocity;
	
	public boolean isGravity;
	
	/**
	 * The constructor.
	 * @param object The object that the component belongs to.
	 */
	public Rigidbody(GameObject object) {
		this.object = object;
		net = new Vector2f(0, 0);
		this.isGravity = true;
	}
	
	/**
	 * Acts upon the member variables, e.g. net, velocity. To be run every frame.
	 */
	public void update() {
		velocity = net.times(object.spriteRenderer.getWindow().time.deltaTime).times(0.005); //F = a
		
		object.transform.position = object.transform.position.plus(velocity);
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
		
		if (isGravity) {
			addForce(new Vector2f(0, -gravityModifier));
		}
			
	}
		
	/**
	 * Adds a force to the object.
	 * @param force
	 */
	public void addForce(Vector2f force) {
		net = net.plus(force);
	}

	/**
	 * Makes the object stop falling.
	 */
	public void stopFalling() {
		if (net.y < 0) net.y = 0;
	}
}
