package main.scripts;

import engine.Animation;
import engine.Animation.*;
import engine.Script;
import engine.graphics.Sprite;
import engine.maths.Vector;
import org.lwjgl.glfw.GLFW;
import engine.GameObject;
import engine.components.*;
import main.Main;

public class Player extends GameObject implements Script {
	private Rigidbody rb;
	private Collider col, gravCol;
	private AnimationController animController;
	
	private Vector startPos;

	Animation customAnim;

	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player(Vector startPos) {
		super(startPos, new Vector(new float[]{1, 2}), Main.window, new Vector(new float[]{0.95f, 0.95f, 1, 0.9f}), "player");
        this.spriteRenderer.sprite.texCoords = new float[]{ //Makes it so that the image fits the object correctly.
            0, 0, //V0
            0, 0.625f, //V1
            0.625f, 0.625f, //V2
            0.625f, 0  //V3
        };
		GameObject.allScripts.add(this);
	}
 
	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		float[] playerSpriteCoords = {
				0, 0, //V0
				0, 0.625f, //V1
				0.625f, 0.625f, //V2
				0.625f, 0  //V3
		};

		rb = new Rigidbody(this);
		col = new Collider(this, rb, false, false);
		gravCol = new Collider(this, rb, false, true);
		animController = new AnimationController(this, new Animation(1f,"idle",this, true, new Frame[] {
				new Frame(new Sprite("player", playerSpriteCoords), 0f)
		}));

		startPos = new Vector(new float[]{0, 0});
		rb.isGravity = true;
		
		gravCol.offset = new Vector(new float[]{0, -transform.size.getAxis(1) * 0.95f / 2});
		gravCol.size = new Vector(new float[]{transform.size.getAxis(0), transform.size.getAxis(1) * 0.1f});

		animController.addAnim(1f,"run",this, true, new Frame[] {
				new Frame(new Sprite("null"), 0000),
				new Frame(new Sprite("ground"), 1000),
				new Frame(new Sprite("ground"), 2000)
		});
		animController.addParam("isRunning");
		animController.addTransition("idle", "run", "isRunning", true);
		animController.addTransition("run", "idle", "isRunning", false);
	}
	
	/**
	 * Runs every frame.
	 */
	public void Update() {
		rb.update();
		col.update();
		gravCol.update();
		animController.update();

		if (input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(new float[]{0, 0});
			transform.position = startPos;
		}

		rb.addForce(new Vector(new float[]{input.getAxisRaw("Horizontal"), 0}).times(0.5f));
		if (gravCol.isColliding) {
			if (input.isKeyDown(GLFW.GLFW_KEY_W) || input.isKeyDown(GLFW.GLFW_KEY_UP) || input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector(new float[]{0, 10f}));
			}
		}

		System.out.println(rb.velocity.getAxis(0));
		animController.setParam("isRunning", (rb.velocity.getAxis(0) > 0.01f || rb.velocity.getAxis(0) < -0.01f) ? true : false);
	}
}
