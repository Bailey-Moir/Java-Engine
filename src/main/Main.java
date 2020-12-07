package main;

import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import main.instances.Player;
import main.instances.TempPlatform;
/**
 * The starting off point.
 * 
 * @author Bailey
 */

public class Main implements Runnable {
	
	private Thread game;
	
	private static Main instance;
	public static Window window;
	private Shader shader;
	
	private final int WIDTH = 1280, HEIGHT = 720;
	private final String TITLE = "big boy";
	private Vector3f background = new Vector3f(0.1f, 0.1f, 0.1f);
	
	private void start() {
		game = new Thread(this, "game");
		game.start(); //Runs the runs function, using Runnable
	}
	
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
		
		new Player(new Vector2f(0, -1));
		new TempPlatform(new Vector2f(-3, -2.5f), new Vector2f(2, 0.5f));
		new TempPlatform(new Vector2f(0, -2f), new Vector2f(1, 1));
		new TempPlatform(new Vector2f(3, -1.5f), new Vector2f(0.5f, 1));
		
		for (GameObject object : GameObject.allObjects) {
			if (object.StartFunc != null) object.StartFunc.accept(0);
		}
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
				
		for (GameObject object : GameObject.allObjects) {
			if (object.UpdateFunc != null) object.UpdateFunc.accept(0);
		}
		
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
