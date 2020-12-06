package main.objects;

import engine.GameObject;
import engine.components.*;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import main.Main;

public class TempPlatform extends GameObject {	
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public TempPlatform(Vector2f position, Vector2f size) {
		super(position, size, Main.window, new Vector3f(0.5f, 0.5f, 0.5f), "ground");
		
		StartFunc = (Integer uselessInt) -> {
			Start();
		};
		/*UpdateFunc = (Integer uselessInt) -> {
			Update();
		};*/
	}

	/**
	 * Runs once when the window is created.
	 */
	private void Start() {
		new Collider(this, true);
	}
	
	/**
	 * Runs every frame.
	 */
	/*private void Update() {
	}*/
}
