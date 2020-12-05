package main;

import org.lwjgl.glfw.GLFW;

import engine.components.*;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
/**
 * The starting off point.
 * 
 * @author Bailey
 */

public class Main implements Runnable {
	
	private Thread game;
	
	private static Main instance;
	protected static Window window;
	private Shader shader;
	
	private final int WIDTH = 1280, HEIGHT = 720;
	private final String TITLE = "big boy";
	private Vector3f background = new Vector3f(0.1f, 0.1f, 0.1f);
	
	private void start() {
		game = new Thread(this, "game");
		game.start(); //Runs the runs function, using Runnable
	}

	private Rigidbody rb;
	
	private void init() {		
		window = new Window(WIDTH, HEIGHT, TITLE);
		shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
		
		window.setBackgroundColour(new Vector3f(background.x, background.y, background.z));
		window.init();
		shader.create();		
		
		window.input.createAxis("Horizontal", new int[] {
				GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D,
				GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_RIGHT
		});
		window.input.createAxis("Vertical", new int[] {
				GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_W,
				GLFW.GLFW_KEY_DOWN, GLFW.GLFW_KEY_UP
		});
		
		rb = new Rigidbody(Objects.player);
		//Objects.player.getComponent(Rigidbody.class);
	}
	
	public void run() {
		init();		
		
		while (!window.shouldClose() && !window.input.isKeyDown(GLFW.GLFW_KEY_GRAVE_ACCENT)) {
			update();
			render();
		}
		close();
	}
	
	private void update() {
		if (window.input.isKeyPressed(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
		if (window.input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			rb.net.set(0, 0);
			Objects.player.transform.position.set(0, 0);
		}
		
		rb.addForce(new Vector2f(window.input.getAxisRaw("Horizontal"), window.input.getAxisRaw("Vertical")));
		rb.update();
		//Objects.player.transform.translate(new Vector2f(window.input.getAxisRaw("Horizontal"), window.input.getAxisRaw("Vertical")));
		
		window.update();
		window.time.update();
	}
	
	private void render() {
		shader.bind();
		Renderer.render();
		
		window.swapBuffers();
		shader.unbind();
	}
	
	private void close() {
		window.destroy();
		shader.destroy();
	}
	
	public static void main(String[] args) {
		instance = new Main();
		instance.start();
	}
}
