package main.scripts;

import engine.GameObject;
import engine.components.*;
import engine.maths.Vector2f;
import engine.maths.Vector4f;
import main.Main;

public class TempPlatform extends GameObject {	
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public TempPlatform(Vector2f position, Vector2f size, Vector4f color) {
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
