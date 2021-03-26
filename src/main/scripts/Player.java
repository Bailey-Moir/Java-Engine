package main.scripts;

import engine.Animation;
import engine.Animation.*;
import engine.Script;
import engine.maths.Vector;
import engine.objects.SpriteSheet;
import org.lwjgl.glfw.GLFW;
import engine.objects.GameObject;
import engine.objects.components.*;
import main.Main;

public class Player extends GameObject {
	private Rigidbody rb;
	private Collider col, gravCol;
	private AnimationController animController;
	
	private Vector startPos;

	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player(Vector startPos) {
		super(Main.window, Vector.square(0, 2), new Vector(new float[]{1.25f, 2.5f}), new Vector(new float[]{1, 1, 1, 1}), 2, "player");
		this.startPos = startPos;
	}
 
	/**
	 * Runs once when the window is created.
	 */
	public void Start() {
		spriteRenderer.spriteSheet = new SpriteSheet("player", new int[] {
			10, 20,
			1, 1
		});

		rb = new Rigidbody(this);
		col = new Collider(this, rb, false, false);
		gravCol = new Collider(this, rb, false, true);
		animController = new AnimationController(this, new Animation(1f,"idle",this, true, new Frame[] {
				new Frame(spriteRenderer.spriteSheet.getSprite(0), 0f)
		}));

		rb.isGravity = true;

		gravCol.offset = new Vector(new float[]{0, -transform.size.getAxis(1) * 0.9f / 2});
		gravCol.size = new Vector(new float[]{transform.size.getAxis(0), transform.size.getAxis(1) * 0.1f});

		animController.addAnim(1f,"run",this, true, new Frame[] {
				new Frame(spriteRenderer.spriteSheet.getSprite(1), 0),
				new Frame(spriteRenderer.spriteSheet.getSprite(0), 200),
				new Frame(spriteRenderer.spriteSheet.getSprite(2), 400),
				new Frame(spriteRenderer.spriteSheet.getSprite(0), 600),
				new Frame(spriteRenderer.spriteSheet.getSprite(0), 800)
		});
		animController.addAnim(1f,"fall",this, true, new Frame[] {
				new Frame(spriteRenderer.spriteSheet.getSprite(3), 0),
				new Frame(spriteRenderer.spriteSheet.getSprite(4), 100),
				new Frame(spriteRenderer.spriteSheet.getSprite(4), 200)
		});
		animController.addParam("isRunning");
		animController.addParam("isFalling");
		animController.addTransition("run", "idle", new String[]{"isRunning"}, new boolean[]{false});
		animController.addTransition("fall", "idle", new String[]{"isRunning", "isFalling"}, new boolean[]{false, false});
		animController.addTransition("idle", "run", new String[]{"isRunning"}, new boolean[]{true});
		animController.addTransition("idle", "fall", new String[]{"isFalling"}, new boolean[]{true});
		animController.addTransition("run", "fall", new String[]{"isFalling"}, new boolean[]{true});
	}

	/**
	 * Runs every frame.
	 */
	public void Update() {
		updateComponents();

		//Teleporting to spawn
		if (Main.window.input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(new float[]{0, 0});
			transform.position = startPos;
		}
		//Crouching
		if (Main.window.input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			if (transform.size.getAxis(1) == 2.5f) {
				transform.size.setAxis(1, 1.25f);
				col.size.setAxis(1, 1.25f);
				gravCol.size.set(new float[]{transform.size.getAxis(0), transform.size.getAxis(1) * 0.1f});
				gravCol.offset.set(new float[]{0, -transform.size.getAxis(1) * 0.9f / 2});
				transform.position.setAxis(1, transform.position.getAxis(1) - 0.625f);
			}
		} else {
			if (transform.size.getAxis(1) == 1.25f) {
				transform.size.setAxis(1, 2.5f);
				col.size.setAxis(1, 2.5f);
				gravCol.size.set(new float[]{transform.size.getAxis(0), transform.size.getAxis(1) * 0.1f});
				gravCol.offset.set(new float[]{0, -transform.size.getAxis(1) * 0.9f / 2});
				transform.position.setAxis(1, transform.position.getAxis(1) + 0.625f);
			}
		}

		//Basic movement
		rb.addForce(new Vector(new float[]{Main.window.input.getAxisRaw("Horizontal"), 0}).times(0.5f));
		//Jumping
		if (gravCol.isColliding) {
			animController.setParam("isFalling", false);
			if (Main.window.input.isKeyDown(GLFW.GLFW_KEY_W) || Main.window.input.isKeyDown(GLFW.GLFW_KEY_UP) || Main.window.input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector(new float[]{0, 10f}));
			}
		} else {
			animController.setParam("isFalling", rb.velocity.getAxis(1) < 0f);
		}

		//Animation
		animController.setParam("isRunning", rb.velocity.getAxis(0) > 0.01f || rb.velocity.getAxis(0) < -0.01f);
		if (Main.window.input.getAxisRaw("Horizontal") < 0) {
			spriteRenderer.flipX = true;
		} else if (Main.window.input.getAxisRaw("Horizontal") > 0) {
			spriteRenderer.flipX = false;
		}
		spriteRenderer.flipY = Main.window.input.isKeyDown(GLFW.GLFW_KEY_S);
	}
}
