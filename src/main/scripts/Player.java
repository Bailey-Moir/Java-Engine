package main.scripts;

import engine.Script;
import engine.maths.Vector;
import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.components.*;
import main.Main;

public class Player extends GameObject implements Script {
	private Rigidbody rb;
	private Collider col, gravCol;
	
	private Vector startPos;

	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player(Vector startPos) {
		super(startPos, new Vector(new float[]{1, 1}), Main.window, new Vector(new float[]{0.95f, 0.95f, 1, 0.9f}), "player");
		GameObject.allScripts.add(this);
	}
 
	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		rb = new Rigidbody(this);
		col = new Collider(this, rb, false, false);
		gravCol = new Collider(this, rb, false, true);

		startPos = new Vector(new float[]{0, 0});
		rb.isGravity = true;
		
		gravCol.offset = new Vector(new float[]{0, -transform.size.getAxis(1) * 0.95f / 2});
		gravCol.size = new Vector(new float[]{transform.size.getAxis(0), transform.size.getAxis(1) * 0.1f});
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

		rb.addForce(new Vector(new float[]{input.getAxisRaw("Horizontal"), 0}).times(0.5f));
		if (gravCol.isColliding) {
			//Stops falling
			//rb.stopFalling();
			
			//Can jump
			if (input.isKeyDown(GLFW.GLFW_KEY_W) || input.isKeyDown(GLFW.GLFW_KEY_UP) || input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector(new float[]{0, 10f}));
			}
		}
	}
}
