package main.objects;

import engine.GameObject;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import main.Main;

public class TempPlatform extends GameObject {	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public TempPlatform(Vector2f position, Vector3f color) {
		super(position, new Vector2f(2, 1), Main.window, color, "platform");
		
		StartFunc = (Integer uselessInt) -> {
			Start();
		};
		UpdateFunc = (Integer uselessInt) -> {
			Update();
		};
	}

	/**
	 * Runs once when the window is created.
	 */
	private void Start() {
	}
	
	/**
	 * Runs every frame.
	 */
	private void Update() {
	}
}
