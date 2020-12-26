package engine.components;

import java.util.ArrayList;
import java.util.List;

import engine.Component;
import engine.GameObject;
import engine.maths.Vector;

/**
 * The collider of an object.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Collider extends Component {
	public Rigidbody rb;
	
	private static final List<Collider> allColliders = new ArrayList<>();
	
	public Vector offset, size;
	
	private final boolean isStatic;
	public boolean isColliding = false, isTrigger;
	
	/**
	 * The constructor for colliders.
	 * @param object The object that the component belongs to.
	 */
	public Collider(GameObject object, Rigidbody rb, boolean isStatic, boolean isTrigger) {
		super(object);
		this.isStatic = isStatic;
		this.isTrigger = isTrigger;
		this.rb = rb;
		
		offset = new Vector(new float[]{0, 0});
		size = object.transform.size;
		
		allColliders.add(this);
	}

	/**
	 * Acts upon the member variables, e.g. <code>isColliding</code>.
	 */
	public void update() {
		boolean isIntercepting = false;
		if (isTrigger) {
			for (Collider col : allColliders) {
				if (col.object == this.object) continue;

				if (!((object.transform.position.getAxis(0) + rb.velocity.getAxis(0) / 100 + offset.getAxis(0) + size.getAxis(0) / 2 < col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2
				|| object.transform.position.getAxis(0) + rb.velocity.getAxis(0) / 100 + offset.getAxis(0) - size.getAxis(0) / 2 > col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2)
				|| (object.transform.position.getAxis(1) + rb.velocity.getAxis(1) / 100 + offset.getAxis(1) + size.getAxis(1) / 2 < col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2
				|| object.transform.position.getAxis(1) + rb.velocity.getAxis(1) / 100 + offset.getAxis(1) - size.getAxis(1) / 2 > col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2))) {
					if (!col.isTrigger) {
						isIntercepting = true;
					}
				}
			}
		}
		if (!isStatic) {
			for (Collider col : allColliders) {
				if (col.object == this.object) continue;

				if (!((object.transform.position.getAxis(0) + rb.velocity.getAxis(0) / 100 + offset.getAxis(0) + size.getAxis(0) / 2 < col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2
				|| object.transform.position.getAxis(0) + rb.velocity.getAxis(0) / 100 + offset.getAxis(0) - size.getAxis(0) / 2 > col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2)
				|| (object.transform.position.getAxis(1) + rb.velocity.getAxis(1) / 100 + offset.getAxis(1) + size.getAxis(1) / 2 < col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2
				|| object.transform.position.getAxis(1) + rb.velocity.getAxis(1) / 100 + offset.getAxis(1) - size.getAxis(1) / 2 > col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2))) {
					if (!col.isTrigger) {
						isIntercepting = true;
					}

					if (!isTrigger && !col.isTrigger) {
						//E.g. the bounds
						double diffL = (col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2 - (object.transform.position.getAxis(0) + offset.getAxis(0) + size.getAxis(0) / 2)) * (col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2 - (object.transform.position.getAxis(0) + offset.getAxis(0) + size.getAxis(0) / 2));
						double diffR = (object.transform.position.getAxis(0) + offset.getAxis(0) - size.getAxis(0) / 2 - (col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2)) * (object.transform.position.getAxis(0) + offset.getAxis(0) - size.getAxis(0) / 2 - (col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2));
						double diffT = (object.transform.position.getAxis(1) + offset.getAxis(1) - size.getAxis(1) / 2 - (col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2)) * (object.transform.position.getAxis(1) + offset.getAxis(1) - size.getAxis(1) / 2 - (col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2));
						double diffB = (col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2 - (object.transform.position.getAxis(1) + offset.getAxis(1) + size.getAxis(1) / 2)) * (col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2 - (object.transform.position.getAxis(1) + offset.getAxis(1) + size.getAxis(1) / 2));

						if (diffL < diffR
								&& diffL < diffT
								&& diffL < diffB) {
							//To the left
							object.transform.position.setAxis(0, col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2 - size.getAxis(0) / 2 + offset.getAxis(0));
						} else if (diffR < diffL
								&& diffR < diffT
								&& diffR < diffB) {
							//To the right
							object.transform.position.setAxis(0, col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2 + size.getAxis(0) / 2 + offset.getAxis(0));
						} else if (diffT < diffL
								&& diffT < diffR
								&& diffT < diffB) {
							//Above
							object.transform.position.setAxis(1, col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2 + size.getAxis(1) / 2 + offset.getAxis(1));
						} else if (diffB < diffL
								&& diffB < diffR
								&& diffB < diffT) {
							//Below
							object.transform.position.setAxis(1, col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2 - size.getAxis(1) / 2 + offset.getAxis(1));
						}
					}
				}
			}
		}
		isColliding = isIntercepting;
	}
}
