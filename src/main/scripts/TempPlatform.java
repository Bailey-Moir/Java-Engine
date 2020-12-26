package main.scripts;

import engine.GameObject;
import engine.Script;
import engine.components.*;
import engine.maths.Vector;
import main.Main;

public class TempPlatform extends GameObject implements Script {
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public TempPlatform(Vector position, Vector size, Vector color) {
		super(position, size, Main.window, color, "ground");
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