package main.scripts;

import engine.Animation;
import engine.Animation.*;
import engine.maths.Vector2;
import engine.maths.Vector4;
import engine.objects.BehaviouralGameObject;
import engine.objects.SpriteSheet;
import org.lwjgl.glfw.GLFW;
import engine.objects.components.*;
import main.Main;

public class Player extends BehaviouralGameObject {
	private Rigidbody rb;
	private Collider col, gravCol;
	private AnimationController animController;
	
	private final Vector2 startPos;

	/**
	 * The constructor.
	 * The only thing you should touch in here are the variables in super().
	 */
	public Player(Vector2 startPos) {
		super(startPos, new Vector2(1, 2), new Vector4(1), 0, "player");
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
		spriteRenderer.spriteSheet.gen();

		rb = new Rigidbody(this);
		col = new Collider(this, rb, false, false);
		gravCol = new Collider(this, rb, false, true);
		animController = new AnimationController(this, new Animation(1f,"idle",this, true, new Frame[] {
				new Frame(spriteRenderer.spriteSheet.getSprite(0), 0f)
		}));

		rb.isGravity = true;

		gravCol.offset = new Vector2(0, -transform.size.y * 0.45f);
		gravCol.size = new Vector2(transform.size.x, transform.size.y * 0.1f);

		animController.addAnim(5f,"run",this, true, new Frame[] {
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
			rb.net.set(0, 0);
			transform.position = startPos;
		}
		//Crouching
		if (Main.window.input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			if (transform.size.y == 2.5f) {
				transform.size.y = 1.25f;
				col.size.y = 1.25f;
				gravCol.size.set(transform.size.x, transform.size.y * 0.1f);
				gravCol.offset.set(0, -transform.size.y * 0.9f / 2);
				transform.position.y -= 0.625f;
			}
		} else {
			if (transform.size.y == 1.25f) {
				transform.size.y = 2.5f;
				col.size.y = 2.5f;
				gravCol.size.set(transform.size.x, transform.size.y * 0.1f);
				gravCol.offset.set(0, -transform.size.y * 0.9f / 2);
				transform.position.y += 0.625f;
			}
		}

		//Basic movement
		rb.addForce(new Vector2(Main.window.input.getAxisRaw("Horizontal"), 0)._times(0.5f));
		//Jumping
		if (gravCol.isColliding) {
			animController.setParam("isFalling", false);
			if (Main.window.input.isKeyDown(GLFW.GLFW_KEY_W) || Main.window.input.isKeyDown(GLFW.GLFW_KEY_UP) || Main.window.input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector2(0, 10f));
			}
		} else {
			animController.setParam("isFalling", rb.velocity.y < 0f);
		}

		//Animation
		animController.setParam("isRunning", rb.velocity.x > 0.01f || rb.velocity.x < -0.01f);
		if (Main.window.input.getAxisRaw("Horizontal") < 0) {
			spriteRenderer.flipX = true;
		} else if (Main.window.input.getAxisRaw("Horizontal") > 0) {
			spriteRenderer.flipX = false;
		}
		spriteRenderer.flipY = Main.window.input.isKeyDown(GLFW.GLFW_KEY_S);
	}
}
