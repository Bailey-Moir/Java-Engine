package main.scripts;

import engine.objects.BehaviouralGameObject;
import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;

public class Platform extends BehaviouralGameObject {
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Platform(Vector position) {
		super(position, new Vector(0.5f, 0.5f), new Vector(1, 1, 1, 1), 1, "block");
	}

	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		new Collider(this, new Rigidbody(this), true, false);
	}

	public void Update() {	}
}