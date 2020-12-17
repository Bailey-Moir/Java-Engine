package main.scripts;

import engine.maths.Vector;
import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.components.*;
import main.Main;

public class Player extends GameObject {
	private Rigidbody rb;
	private Collider col, gravCol;
	
	private Vector startPos;
	
	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player(Vector startPos) {
		super(startPos, new Vector(new float[]{1, 1}), Main.window, new Vector(new float[]{1, 1, 1, 1}), "player");
				
		this.startPos = startPos;
	}
 
	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		rb = new Rigidbody(this);
		col = new Collider(this, rb);
		gravCol = new Collider(this, rb);
		
		gravCol.offset = new Vector(new float[]{0, -transform.size.getAxis(1) * 0.95f / 2});
		gravCol.size = new Vector(new float[]{transform.size.getAxis(0) * 0.9f, transform.size.getAxis(1) * 0.1f});
		gravCol.isTrigger = true;
	}
	
	/**
	 * Runs every frame.
	 */
	public void Update() {
		if (input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(new float[]{0, 0});
			transform.position = startPos;
		}

		rb.update();
		col.update();
		gravCol.update();
		
		rb.isGravity = !gravCol.isColliding;
		rb.addForce(new Vector(new float[]{input.getAxisRaw("Horizontal"), input.getAxisRaw("Vertical")}).times(0.5f));
		if (col.isColliding) {
			//Stops falling
			rb.stopFalling();
			
			//Can jump
			if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector(new float[]{0, 7.5f}));
			}
		}
	}
}
