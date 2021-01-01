package main.scripts;

import engine.Animation;
import engine.Animation.*;
import engine.Script;
import engine.objects.Sprite;
import engine.maths.Vector;
import engine.objects.SpriteSheet;
import org.lwjgl.glfw.GLFW;
import engine.objects.GameObject;
import engine.objects.components.*;
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
		super(startPos, new Vector(new float[]{1.25f, 2.5f}), Main.window, new Vector(new float[]{1, 1, 1, 1}), "player/player");
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

		startPos = Vector.square(0, 2);
		rb.isGravity = true;
		rb.mass = 1;
		
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
		rb.update();
		col.update();
		gravCol.update();
		animController.update();

		//Teleporting to spawn
		if (input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(new float[]{0, 0});
			transform.position = startPos;
		}
		//Crouching
		if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			if (transform.size.getAxis(1) == 2) {
				transform.position.setAxis(1, transform.position.getAxis(1) - 0.5f);
				transform.size.setAxis(1, 1);
			}
		} else {
			if (transform.size.getAxis(1) == 1) {
				transform.position.setAxis(1, transform.position.getAxis(1) + 0.5f);
				transform.size.setAxis(1, 2);
			}
		}

		//Basic movement
		rb.addForce(new Vector(new float[]{input.getAxisRaw("Horizontal"), 0}).times(0.5f));
		//Jumping
		if (gravCol.isColliding) {
			rb.stopFalling();
			animController.setParam("isFalling", false);
			if (input.isKeyDown(GLFW.GLFW_KEY_W) || input.isKeyDown(GLFW.GLFW_KEY_UP) || input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				rb.addForce(new Vector(new float[]{0, 10f}));
			}
		} else {
			if (rb.velocity.getAxis(1) < 0) {
				animController.setParam("isFalling", true);
			} else {
				animController.setParam("isFalling", false);
			}
		}

		//Animation
		animController.setParam("isRunning", (rb.velocity.getAxis(0) > 0.01f || rb.velocity.getAxis(0) < -0.01f) ? true : false);
		if (input.getAxisRaw("Horizontal") < 0) {
			spriteRenderer.flipX = true;
		} else if (input.getAxisRaw("Horizontal") > 0) {
			spriteRenderer.flipX = false;
		}
		spriteRenderer.flipY = input.isKeyDown(GLFW.GLFW_KEY_S);
	}
}
