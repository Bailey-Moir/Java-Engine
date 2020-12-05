package main.objects;

import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.components.Rigidbody;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import main.Main;

public class Player extends GameObject {
	private Rigidbody rb;
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player() {
		super(new Vector2f(0, 0), new Vector2f(1, 1), Main.window, new Vector3f(1, 1, 1), "player");
		
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
		rb = new Rigidbody(this);
		rb.isGravity = false;
	}
	
	/**
	 * Runs every frame.
	 */
	private void Update() {
		if (input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(0, 0);
			transform.position.set(0, 0);
		}
		
		rb.addForce(new Vector2f(input.getAxisRaw("Horizontal"), input.getAxisRaw("Vertical")));
		rb.update();
	}
}
