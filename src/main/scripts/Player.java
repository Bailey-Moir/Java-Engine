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
		super(startPos, new Vector(new float[]{1, 2}), Main.window, new Vector(new float[]{1, 1, 1, 1}), "player");
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
		float[] specialCoords = {
				0, 0, //V0
				0, 0.625f, //V1
				0.625f, 0.625f, //V2
				0.625f, 0  //V3
		};

		rb = new Rigidbody(this);
		col = new Collider(this, rb, false, false);
		gravCol = new Collider(this, rb, false, true);
		animController = new AnimationController(this, new Animation(1f,"idle",this, true, new Frame[] {
				new Frame(new Sprite("player", specialCoords), 0f)
		}));

		startPos = new Vector(new float[]{0, 0});
		rb.isGravity = true;
		
		gravCol.offset = new Vector(new float[]{0, -transform.size.getAxis(1) * 0.95f / 2});
		gravCol.size = new Vector(new float[]{transform.size.getAxis(0), transform.size.getAxis(1) * 0.1f});

		animController.addAnim(1f,"run",this, true, new Frame[] {
				new Frame(new Sprite("running1", specialCoords), 0),
				new Frame(new Sprite("player", specialCoords), 200),
				new Frame(new Sprite("running2", specialCoords), 400),
				new Frame(new Sprite("player", specialCoords), 600),
				new Frame(new Sprite("player", specialCoords), 800)
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
		if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			transform.size.setAxis(1, 1);
		} else {
			transform.size.setAxis(1, 2);
		}

		rb.addForce(new Vector(new float[]{input.getAxisRaw("Horizontal"), 0}).times(0.5f));
		if (gravCol.isColliding) {
			if (input.isKeyDown(GLFW.GLFW_KEY_W) || input.isKeyDown(GLFW.GLFW_KEY_UP) || input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector(new float[]{0, 10f}));
			}
		}

		animController.setParam("isRunning", (rb.velocity.getAxis(0) > 0.01f || rb.velocity.getAxis(0) < -0.01f) ? true : false);
		if (input.getAxisRaw("Horizontal") < 0) {
			spriteRenderer.flipX = true;
		} else if (input.getAxisRaw("Horizontal") > 0) {
			spriteRenderer.flipX = false;
		}
		spriteRenderer.flipY = input.isKeyDown(GLFW.GLFW_KEY_S);
	}
}
