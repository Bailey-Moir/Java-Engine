package main.scripts;

import engine.GameObject;
import engine.components.*;
import engine.maths.Vector;
import main.Main;

public class TempPlatform extends GameObject {	
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public TempPlatform(Vector position, Vector size, Vector color) {
		super(position, size, Main.window, color, "ground");
	}

	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		new Collider(this);
	}
	
	public void Update() {
	}
}
