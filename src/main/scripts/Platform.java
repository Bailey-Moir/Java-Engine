package main.scripts;

import engine.maths.Vector2;
import engine.maths.Vector4;
import engine.objects.BehaviouralGameObject;
import engine.objects.components.*;

public class Platform extends BehaviouralGameObject {
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Platform(Vector2 position) {
		super(position, new Vector2(1, 1), new Vector4( 1), 1, "tester");
	}

	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		new Collider(this, new Rigidbody(this), true, false);
	}

	public void Update() {	}
}