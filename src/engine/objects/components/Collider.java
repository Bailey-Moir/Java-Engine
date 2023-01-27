package engine.objects.components;

import java.util.ArrayList;
import java.util.List;

import engine.maths.Vector2;
import engine.objects.Component;
import engine.objects.GameObject;
import engine.maths.Mathl;

/**
 * The collider of a parent.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Collider extends Component {
	public Rigidbody rb;
	
	private static final List<Collider> allColliders = new ArrayList<>();

	public List<Collider> collidingWith = new ArrayList<>();
	public Vector2 offset, size;
	
	public final boolean isStatic;
	public boolean isColliding = false, isTrigger;
	
	/**
	 * The constructor for colliders.
	 * @param parent The parent that the component belongs to.
	 */
	public Collider(GameObject parent, Rigidbody rb, boolean isStatic, boolean isTrigger) {
		super(parent);
		this.isStatic = isStatic;
		this.isTrigger = isTrigger;
		this.rb = rb;
		
		offset = Vector2.zero();
		size = new Vector2(parent.transform.size);
		
		allColliders.add(this);
	}

	/**
	 * Acts upon the member variables, e.g. <code>isColliding</code>.
	 */
	@Override
	public void update() {
		boolean isIntercepting = false;
		collidingWith.clear();
		for (Collider col : allColliders) {
			if (col.parent == this.parent) continue;
			if (willIntercept(col)) {
				//If it is going to intercept and the other collider isn't a trigger, it registers.
				if (!col.isTrigger) {
					isIntercepting = true;
					collidingWith.add(col);

					//If neither colliders are triggers and this is not static, then keep self out of other colliders.
					if (!isTrigger && !isStatic) {
						//E.g. the bounds
						double[] margins = {
								//rightMargin
								Math.abs(getSide(col, false, -1) - getSide(this, false, 1)),
								//leftMargin
								Math.abs(getSide(this, false, -1) - getSide(col, false, 1)),
								//bottomMargin
								Math.abs(getSide(this, true, -1) - getSide(col, true, 1)),
								//topMargin
								Math.abs(getSide(col, true, -1) - getSide(this, true, 1))
						};

						// Deletes the force of the direction that the collider is colliding with.
						int indexLargest = Mathl.minI(margins);
						switch (Mathl.minI(margins)) {
							case 0:
								//To the left
								if (rb.net.x > 0) rb.net.x = 0;
								parent.transform.position.x = col.parent.transform.position.x + col.offset.x - col.size.x / 2 - size.x / 2 + offset.x;
								break;
							case 1:
								//To the right
								if (rb.net.x < 0) rb.net.x = 0;
								parent.transform.position.x = col.parent.transform.position.x + col.offset.x + col.size.x / 2 + size.x / 2 + offset.x;
								break;
							case 2:
								//Above
								if (rb.net.y < 0) rb.net.y = 0;
								parent.transform.position.y = col.parent.transform.position.y + col.offset.y + col.size.y / 2 + size.y / 2 + offset.y;

								break;
							case 3:

								break;
						}
						if (indexLargest == 0) {
							//To the left
							if (rb.net.x > 0) rb.net.x = 0;
							parent.transform.position.x = col.parent.transform.position.x + col.offset.x - col.size.x / 2 - size.x / 2 + offset.x;
						} else if (indexLargest == 1) {
							//To the right
							if (rb.net.x < 0) rb.net.x = 0;
							parent.transform.position.x = col.parent.transform.position.x + col.offset.x + col.size.x / 2 + size.x / 2 + offset.x;
						} else if (indexLargest == 2) {
							//Above
							if (rb.net.y < 0) rb.net.y = 0;
							parent.transform.position.y = col.parent.transform.position.y + col.offset.y + col.size.y / 2 + size.y / 2 + offset.y;
						} else if (indexLargest == 3) {
							//Below
							if (rb.net.y > 0) rb.net.y = 0;
							parent.transform.position.y = col.parent.transform.position.y + col.offset.y - col.size.y / 2 - size.y / 2 + offset.y;
						}
					}
				}
			}
		}
		isColliding = isIntercepting;
	}

	private boolean willIntercept(Collider col) {
		return !((parent.transform.position.x + rb.velocity.x / 100 + offset.x + size.x / 2 < col.parent.transform.position.x + col.offset.x - col.size.x / 2
				|| parent.transform.position.x + rb.velocity.x / 100 + offset.x - size.x / 2 > col.parent.transform.position.x + col.offset.x + col.size.x / 2)
				|| (parent.transform.position.y + rb.velocity.y / 100 + offset.y + size.y / 2 < col.parent.transform.position.y + col.offset.y - col.size.y / 2
				|| parent.transform.position.y + rb.velocity.y / 100 + offset.y - size.y / 2 > col.parent.transform.position.y + col.offset.y + col.size.y / 2));
	}

	private double getSide(Collider col, boolean vertical, int side) {
		return vertical ?
				col.parent.transform.position.y + col.offset.y + side * col.size.y / 2
			:
				col.parent.transform.position.x + col.offset.x + side * col.size.x / 2;
	}
}
