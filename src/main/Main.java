package main;

import org.lwjgl.glfw.GLFW;

import engine.Camera;
import engine.GameObject;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.maths.Vector4f;
import main.scripts.Player;
import main.scripts.TempPlatform;

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
	
	private GameObject player;

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
		
		player = new Player(new Vector2f(0, -1));
		new TempPlatform(new Vector2f(-3, -2.5f), new Vector2f(2, 0.5f), new Vector4f(1, 0.5f, 0, 1));
		new TempPlatform(new Vector2f(0, -2f), new Vector2f(1, 1), new Vector4f(0.5f, 0.25f, 0, 1));
		new TempPlatform(new Vector2f(3, -1.5f), new Vector2f(2, 5), new Vector4f(0.1f, 0.05f, 0, 1));
		
		for (GameObject object : GameObject.allObjects) {
			object.Start();
		}
		
		Renderer.currentCamera = new Camera(window.input.getMousePos());
		
		//window.setFullscreen(true);
	}
	
	public void run() {
		init();		
		
		while (!window.shouldClose() && !window.input.isKeyDown(GLFW.GLFW_KEY_GRAVE_ACCENT)) {
			update();
			render();
		}
		close();
	}
	
	boolean cameraToggle = true;
	private void update() {
		if (window.input.isKeyPressed(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
				
		for (GameObject object : GameObject.allObjects) {
			object.Update();
		}
		
		if (window.input.isKeyPressed(GLFW.GLFW_KEY_0)) cameraToggle = !cameraToggle;
		
		if (cameraToggle) Renderer.currentCamera.position = new Vector2f(window.input.getMousePos().x * -1, window.input.getMousePos().y).times(0.025f);
		else Renderer.currentCamera.position = player.transform.position;
		
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
