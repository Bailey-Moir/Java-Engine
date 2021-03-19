package main.scripts;

import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;

public class Platform extends GameObject {
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Platform(Vector position) {
		super(Main.instance.window, position, new Vector(new float[]{4, 2}), new Vector(new float[]{1, 1, 1, 1}), 1, "ground");
	}

	/**
	 * Runs once when the window is created.
	 */
	@Override
	public void StartUpdate() {
		new Collider(this, new Rigidbody(this), true, false);
	}
	@Override
	public void StartRender() {}
	@Override
	public void Update() {
	}
}