package main;

import engine.Script;
import engine.maths.Vector;
import main.scripts.CameraController;
import main.scripts.Gate;
import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.io.Window;
import main.scripts.Player;
import main.scripts.TempPlatform;

/**
 * The starting off point.
 * 
 * @author Bailey
 */

public class Main implements Runnable {
	Thread game;
	
	static Main instance;
	public static Window window;
	Shader shader;
	
	final int WIDTH = 1280, HEIGHT = 720;
	final String TITLE = "big boy";
	Vector background = new Vector(new float[]{0.1f, 0.1f, 0.12f});

	private void start() {
		game = new Thread(this, "game");
		game.start(); //Runs the runs function, using Runnable
	}
	
	private void init() {
		window = new Window(WIDTH, HEIGHT, TITLE);
		shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
		
		window.setBackgroundColour(background);
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

		new Gate(new Vector(new float[]{4.5f, 0.75f}), new Vector(new float[]{1, 1.5f}), new Vector(new float []{0.95f, 0.95f, 1, 0.5f}));
		new TempPlatform(new Vector(new float[]{-4, -2.5f}), new Vector(new float[]{3, 1}), new Vector(new float[]{0.95f, 0.95f, 1, 1}));
		new TempPlatform(new Vector(new float[]{0, -2f}), new Vector(new float[]{3, 1}), new Vector(new float[]{0.95f, 0.95f, 1, 1}));
		new TempPlatform(new Vector(new float[]{4, -1.5f}), new Vector(new float[]{2, 1}), new Vector(new float[]{0.95f, 0.95f, 1, 1}));
		new CameraController(new Player(new Vector(new float[]{0, 2})), window);

		for (Script script : GameObject.allScripts) {
			script.Start();
		}
		
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

	private void update() {
		if (window.input.isKeyPressed(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
				
		for (Script object : GameObject.allScripts) {
			object.Update();
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
