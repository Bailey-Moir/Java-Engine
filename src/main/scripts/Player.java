package main.scripts;


import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.components.*;
import engine.maths.Vector2f;
import engine.maths.Vector4f;
import main.Main;

public class Player extends GameObject {
	private Rigidbody rb;
	private Collider col, gravCol;
	
	private Vector2f startPos;
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player(Vector2f startPos) {
		super(startPos, new Vector2f(1, 1), Main.window, new Vector4f(1, 1, 1, 1), "player");
				
		this.startPos = startPos;
	}
 
	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		rb = new Rigidbody(this);
		col = new Collider(this, rb);
		gravCol = new Collider(this, rb);
		
		gravCol.offset = new Vector2f(0, -transform.size.y * 0.95f / 2);
		gravCol.size = new Vector2f(transform.size.x * 0.9f, transform.size.y * 0.1f);
		gravCol.isTrigger = true;
	}
	
	/**
	 * Runs every frame.
	 */
	public void Update() {
		if (input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(0, 0);
			transform.position = startPos;
		}
		
		rb.update();
		col.update();
		
		gravCol.update();
		
		rb.isGravity = !gravCol.isColliding;
		rb.addForce(new Vector2f(input.getAxisRaw("Horizontal"), input.getAxisRaw("Vertical")).times(0.5f));
		if (col.isColliding) {
			//Stops falling
			rb.stopFalling();
			
			//Can jump
			if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector2f(0, 7.5f));
			}
		}
	}
}
