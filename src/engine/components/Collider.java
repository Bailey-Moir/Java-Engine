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
			boolean isIntercepting = false;
			for (Collider col : allColliders) {		
				if (col.object == this.object) continue;

				if ((object.transform.position.getAxis(0) + rb.velocity.getAxis(0) + offset.getAxis(0) + size.getAxis(0) / 2 < col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2
					|| object.transform.position.getAxis(0) + rb.velocity.getAxis(0) + offset.getAxis(0) - size.getAxis(0) / 2 > col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2)
					|| (object.transform.position.getAxis(1) + rb.velocity.getAxis(1) + offset.getAxis(1) + size.getAxis(1) / 2 < col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2
					|| object.transform.position.getAxis(1) + rb.velocity.getAxis(1) + offset.getAxis(1) - size.getAxis(1) / 2 > col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2)) {
					//Not-Intercepting
					System.out.println("-");
				} else {
					System.out.println("+");
					isIntercepting = true;

					if (!isTrigger) {
						//E.g. the bounds
						float diffL = col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2 - (object.transform.position.getAxis(0) + offset.getAxis(0) + size.getAxis(0) / 2);
						float diffR = object.transform.position.getAxis(0) + offset.getAxis(0) - size.getAxis(0) / 2 - (col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2);
						float diffT = object.transform.position.getAxis(1) + offset.getAxis(1) - size.getAxis(1) / 2 - (col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2);
						float diffB = col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2 - (object.transform.position.getAxis(1) + offset.getAxis(1) + size.getAxis(1) / 2);

						if (diffL > diffR
						 && diffL > diffT
						 && diffL > diffB) {
							//To the left
							object.transform.position.setAxis(0, col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2  - size.getAxis(0) / 2 + offset.getAxis(0));
						} else if (diffR > diffL
								&& diffR > diffT
								&& diffR > diffB) {
							//To the right
							object.transform.position.setAxis(0, col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2  + size.getAxis(0) / 2 + offset.getAxis(0));
						} else if (diffT > diffL
								&& diffT > diffR
								&& diffT > diffB) {
							//Above
							object.transform.position.setAxis(1, col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2  + size.getAxis(1) / 2 + offset.getAxis(1));
						} else if (diffB > diffL
								&& diffB > diffR
								&& diffB > diffT) {
							//Below
							object.transform.position.setAxis(1, col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2  - size.getAxis(1) / 2 + offset.getAxis(1));
						}
					}
				}
			}
			isColliding = isIntercepting;
		}
	}
}
