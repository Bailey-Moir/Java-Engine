package engine.components;

import java.util.ArrayList;
import java.util.List;

import engine.GameObject;
import engine.maths.Vector;

/**
 * The collider of an object.
 * 
 * @author Bailey
 */

public class Collider {
	private GameObject object;
	public Rigidbody rb;
	
	private static List<Collider> allColliders = new ArrayList<Collider>();
	
	public Vector offset, size;
	
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

		
		offset = new Vector(new float[]{0, 0});
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
		
		offset = new Vector(new float[]{0, 0});
		size = object.transform.size;
		
		allColliders.add(this);
	}

	/**
	 * Acts upon the member variables, e.g. <code>isColliding</code>.
	 */
	public void update() {
		if (!isStatic) {
			//rb.velocity = rb.velocity.minus(rb.velocity);
			boolean isIntercepting = false;
			for (Collider col : allColliders) {		
				if (col.object == this.object) continue;
				if (col.isStatic) {
					if ((rb.velocity.dimensions.get(0) + offset.dimensions.get(0) + size.dimensions.get(0) / 2 < col.object.transform.position.dimensions.get(0) + col.offset.dimensions.get(0) - col.size.dimensions.get(0) / 2
						|| rb.velocity.dimensions.get(0) + offset.dimensions.get(0) - size.dimensions.get(0) / 2 > col.object.transform.position.dimensions.get(0) + col.offset.dimensions.get(0) + col.size.dimensions.get(0) / 2)
						|| (rb.velocity.dimensions.get(1) + offset.dimensions.get(1) + size.dimensions.get(1) / 2 < col.object.transform.position.dimensions.get(1) + col.offset.dimensions.get(1) - col.size.dimensions.get(1) / 2
						|| rb.velocity.dimensions.get(1) + offset.dimensions.get(1) - size.dimensions.get(1) / 2 > col.object.transform.position.dimensions.get(1) + col.offset.dimensions.get(1) + col.size.dimensions.get(1) / 2)) {
						//Not-Intercepting
					} else {
						System.out.println("thing");
						isIntercepting = true;

						if (!isTrigger) {
							//E.g. the bounds

						}
					}
				} else {
					/*
					 * If not intercepting
					 *
					 * If (All ||)
					 * 	right side is further left than the subject's left
					 *  left side is further right than the subject's right
					 *  top side is further down the the subject's bottom side
					 *  bottom side is further up than the subject's top side
					 */
					if ((rb.velocity.dimensions.get(0) + offset.dimensions.get(0) + size.dimensions.get(0) / 2 < col.rb.velocity.dimensions.get(0) + col.offset.dimensions.get(0) - col.size.dimensions.get(0) / 2
						|| rb.velocity.dimensions.get(0) + offset.dimensions.get(0) - size.dimensions.get(0) / 2 > col.rb.velocity.dimensions.get(0) + col.offset.dimensions.get(0) + col.size.dimensions.get(0) / 2)
						|| (rb.velocity.dimensions.get(1) + offset.dimensions.get(1) + size.dimensions.get(1) / 2 < col.rb.velocity.dimensions.get(1) + col.offset.dimensions.get(1) - col.size.dimensions.get(1) / 2
						|| rb.velocity.dimensions.get(1) + offset.dimensions.get(1) - size.dimensions.get(1) / 2 > col.rb.velocity.dimensions.get(1) + col.offset.dimensions.get(1) + col.size.dimensions.get(1) / 2)) {
						//Not-Intercepting
					} else {
						System.out.println("thing2");
						isIntercepting = true;

						if (!isTrigger) {
							//E.g. the bounds

						}
					}
				}
			}
			isColliding = isIntercepting;
		}
	}
}
