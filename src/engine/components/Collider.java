package engine.components;

import java.util.ArrayList;
import java.util.List;

import engine.GameObject;
import engine.GameObject.Transform;
import engine.maths.Vector2f;

public class Collider {
	private GameObject object;
	
	private static List<Collider> allColliders = new ArrayList<Collider>();
	
	public boolean isStatic, isColliding;
	
	/**
	 * The constructor.
	 * @param object The object that the component belongs to.
	 */
	public Collider(GameObject object, boolean isStatic) {
		this.object = object;
		this.isStatic = isStatic;
		
		allColliders.add(this);
	}
	
	/**
	 * Acts upon the member variables, e.g. isColliding.
	 */
	public void update() {
		if (!isStatic) {
			boolean isIntercepting = false;
			for (Collider col : allColliders) {
				Transform subjectTrans = col.object.transform;
				Transform localTrans = object.transform;
				
				if (col != this) {
					/*
					 * If not intercepting
					 * 
					 * If (All ||)
					 * 	right side is further left than the subject's left 
					 *  left side is further right than the subject's right
					 *  top side is further down the the subject's bottom side
					 *  bottom side is further up than the subject's top side
					 */
					if ((localTrans.position.x + localTrans.size.x / 2 < subjectTrans.position.x - subjectTrans.size.x / 2 || localTrans.position.x - localTrans.size.x / 2 > subjectTrans.position.x + subjectTrans.size.x / 2)
					 || (localTrans.position.y + localTrans.size.y / 2 < subjectTrans.position.y - subjectTrans.size.y / 2 || localTrans.position.y - localTrans.size.y / 2 > subjectTrans.position.y + subjectTrans.size.y / 2)) {
						//Not-Intercepting
					} else {
						isIntercepting = true;
					}
				}
			}
			isColliding = isIntercepting;	
		}
	}
}
