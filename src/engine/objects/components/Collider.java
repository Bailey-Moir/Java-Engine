package engine.objects.components;

import java.util.ArrayList;
import java.util.List;

import engine.objects.GameObject;
import engine.maths.Mathl;
import engine.maths.Vector;

/**
 * The collider of an object.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Collider {
	public GameObject object;
	public Rigidbody rb;
	
	private static final List<Collider> allColliders = new ArrayList<>();

	public List<Collider> collidingWith = new ArrayList<>();
	public Vector offset, size;
	
	public final boolean isStatic;
	public boolean isColliding = false, isTrigger;
	
	/**
	 * The constructor for colliders.
	 * @param object The object that the component belongs to.
	 */
	public Collider(GameObject object, Rigidbody rb, boolean isStatic, boolean isTrigger) {
		this.object = object;
		this.isStatic = isStatic;
		this.isTrigger = isTrigger;
		this.rb = rb;
		
		offset = new Vector(new float[]{0, 0});
		size = new Vector(object.transform.size);
		
		allColliders.add(this);
	}

	/**
	 * Acts upon the member variables, e.g. <code>isColliding</code>.
	 */
	public void update() {
		boolean isIntercepting = false;
		collidingWith.clear();
		for (Collider col : allColliders) {
			if (col.object == this.object) continue;

			if (!willIntercept(col)) {
				//If is going to intercept and the other collider isn't a trigger, it registers.
				if (!col.isTrigger) {
					isIntercepting = true;
					collidingWith.add(col);
				}

				//If neither colliders are triggers and this is not static, then keep self out of other colliders.
				if (!isTrigger && !col.isTrigger && !isStatic) {
					//E.g. the bounds
					double[] margins = {
					/*rightMargin*/ Math.pow(getSide(col, 0, -1) - getSide(this, 0, 1), 2),
					/*leftMargin*/ Math.pow(getSide(this, 0, -1) - getSide(col, 0, 1), 2),
					/*bottomMargin*/ Math.pow(getSide(this, 1, -1) - getSide(col, 1, 1), 2),
					/*topMargin*/ Math.pow(getSide(col, 1, -1) - getSide(this, 1, 1), 2)
					};

					int indexLargest = Mathl.minI(margins);
					if (indexLargest == 0) {
						//To the left
						if (rb.net.getAxis(0) > 0) rb.net.setAxis(0, 0);
						object.transform.position.setAxis(0, col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2 - size.getAxis(0) / 2 + offset.getAxis(0));
					} else if (indexLargest == 1) {
						//To the right
						if (rb.net.getAxis(0) < 0) rb.net.setAxis(0, 0);
						object.transform.position.setAxis(0, col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2 + size.getAxis(0) / 2 + offset.getAxis(0));
					} else if (indexLargest == 2) {
						//Above
						if (rb.net.getAxis(1) < 0) rb.net.setAxis(1, 0);
						object.transform.position.setAxis(1, col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2 + size.getAxis(1) / 2 + offset.getAxis(1));
					} else if (indexLargest == 3) {
						//Below
						if (rb.net.getAxis(1) > 0) rb.net.setAxis(1, 0);
						object.transform.position.setAxis(1, col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2 - size.getAxis(1) / 2 + offset.getAxis(1));
					}
				}
			}
		}
		isColliding = isIntercepting;
	}

	private boolean willIntercept(Collider col) {
		return ((object.transform.position.getAxis(0) + rb.velocity.getAxis(0) / 100 + offset.getAxis(0) + size.getAxis(0) / 2 < col.object.transform.position.getAxis(0) + col.offset.getAxis(0) - col.size.getAxis(0) / 2
				|| object.transform.position.getAxis(0) + rb.velocity.getAxis(0) / 100 + offset.getAxis(0) - size.getAxis(0) / 2 > col.object.transform.position.getAxis(0) + col.offset.getAxis(0) + col.size.getAxis(0) / 2)
				|| (object.transform.position.getAxis(1) + rb.velocity.getAxis(1) / 100 + offset.getAxis(1) + size.getAxis(1) / 2 < col.object.transform.position.getAxis(1) + col.offset.getAxis(1) - col.size.getAxis(1) / 2
				|| object.transform.position.getAxis(1) + rb.velocity.getAxis(1) / 100 + offset.getAxis(1) - size.getAxis(1) / 2 > col.object.transform.position.getAxis(1) + col.offset.getAxis(1) + col.size.getAxis(1) / 2));
	}

	private double getSide(Collider col, int axis, int side) {
		return col.object.transform.position.getAxis(axis) + col.offset.getAxis(axis) + side * col.size.getAxis(axis) / 2;
	}
}
