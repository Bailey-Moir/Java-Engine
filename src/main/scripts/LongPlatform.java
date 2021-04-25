package main.scripts;

import engine.Script;
import engine.maths.Vector;
import engine.objects.GameObject;
import engine.objects.components.Collider;
import engine.objects.components.Rigidbody;
import main.Main;

public class LongPlatform extends GameObject {

	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public LongPlatform(Vector position) {
		super(Main.window, position, new Vector(8, 2), new Vector(1, 1, 1, 1), 1, "long ground");
	}

	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		new Collider(this, new Rigidbody(this), true, false);
	}

	public void Update() {}
}