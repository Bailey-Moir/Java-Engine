package main.objects;

import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.components.*;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import main.Main;

public class Player extends GameObject {
	private Rigidbody rb;
	private Collider col;
	
	private Vector2f startPos;
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player(Vector2f startPos) {
		super(startPos, new Vector2f(1, 1), Main.window, new Vector3f(1, 1, 1), "player");
		
		this.startPos = startPos;
		
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
		col = new Collider(this, false);
	}
	
	/**
	 * Runs every frame.
	 */
	private void Update() {
		if (input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(0, 0);
			transform.position = startPos;
		}

		col.update();
		rb.isGravity = !col.isColliding;
		rb.addForce(new Vector2f(input.getAxisRaw("Horizontal"), input.getAxisRaw("Vertical")).times(0.5));
		if (col.isColliding) {
			//Stops falling
			rb.stopFalling();
			
			//Can jump
			if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector2f(0, 7.5));
			}
		}
		rb.update();
	}
}
