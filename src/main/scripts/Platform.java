package main.scripts;

import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;

public class Platform extends GameObject implements Script {
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Platform(Vector position) {
		super(position, new Vector(new float[]{4, 2}), Main.window, new Vector(new float[]{1, 1, 1, 1}), 0, "ground");
		GameObject.allScripts.add(this);
	}

	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		new Collider(this, new Rigidbody(this), true, false);
	}

	public void Update() {
	}
}