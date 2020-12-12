package engine.components;

import java.util.ArrayList;
import java.util.List;

import engine.GameObject;
import engine.GameObject.Transform;
import engine.maths.Vector2f;

/**
 * The collider of an object.
 * 
 * @author Bailey
 */

public class Collider {
	private GameObject object;
	public Rigidbody rb;
	
	private static List<Collider> allColliders = new ArrayList<Collider>();
	
	public Vector2f offset, size;
	
	private boolean isStatic;
	public boolean isColliding, isTrigger;
	
	/**
	 * The constructor for static colliders.
	 * @param object The object that the component belongs to.
	 */
	public Collider(GameObject object) {		
		isStatic = true;
		isTrigger = false;
		this.object = object;
		
		offset = new Vector2f(0, 0);
		size = object.transform.size;
		
		allColliders.add(this);
	}

	/**
	 * The constructor for non-static colliders.
	 * @param object The object that the component belongs to.
	 */
	public Collider(GameObject object, Rigidbody rb) {
		isStatic = false;
		isTrigger = false;
		this.object = object;
		this.rb = rb;
		
		offset = new Vector2f(0, 0);
		size = object.transform.size;
		
		allColliders.add(this);
	}
	
	private Vector2f lastVelocity = new Vector2f(0, 0);
	/**
	 * Acts upon the member variables, e.g. <code>isColliding</code>.
	 */
	public void update() {
		if (!isStatic) {
			//object.transform.position = object.transform.position.minus(rb.velocity);
			boolean isIntercepting = false;
			for (Collider col : allColliders) {		
				if (col.object == this.object) continue;
				/*
				 * If not intercepting
				 * 
				 * If (All ||)
				 * 	right side is further left than the subject's left 
				 *  left side is further right than the subject's right
				 *  top side is further down the the subject's bottom side
				 *  bottom side is further up than the subject's top side
				 */
				if ((object.transform.position.x + offset.x + size.x / 2 < col.object.transform.position.x + col.offset.x - col.size.x / 2 
				|| object.transform.position.x + offset.x - size.x / 2 > col.object.transform.position.x + col.offset.x + col.size.x / 2)
				|| (object.transform.position.y + offset.y + size.y / 2 < col.object.transform.position.y + col.offset.y - col.size.y / 2
				|| object.transform.position.y + offset.y - size.y / 2 > col.object.transform.position.y + col.offset.y + col.size.y / 2)) {
					//Not-Intercepting
				} else {
					isIntercepting = true;

					if (!isTrigger) {
						
					}
				}
			}
			isColliding = isIntercepting;
			lastVelocity = rb.velocity;
		}
	}
}
