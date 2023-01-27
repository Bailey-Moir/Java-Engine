package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import engine.Script;
import engine.graphics.shaders.StaticShader;
import engine.io.Window;
import engine.maths.Vector2;
import engine.maths.Vector4;
import engine.objects.GameObject;
import engine.objects.GameObject.SpriteRenderer;
import main.scripts.CameraController;
import main.scripts.Platform;
import main.scripts.Player;

/**
 * The starting off point.
 *
 * TODO Create lighting, e.g. light for the portal.
 *
 * @author Bailey
 */

public class Main implements Runnable {
	Thread game;
	
	static Main instance;
	public static Window window;

	StaticShader shader;
	
	final int WIDTH = 2000, HEIGHT = 1500;
	final String TITLE = "Debugging";
	Vector4 background = new Vector4(0.1f, 0.1f, 0.12f, 1.0f);

	private void start() {
		game = new Thread(this, "game");
		game.start(); //Runs the runs function, using Runnable
	}
	
	private void init() {
		window = new Window(WIDTH, HEIGHT, TITLE);
		window.setBackgroundColour(background);
		window.init();

		shader = new StaticShader();

		//new GateCover(new Vector(10f, 1.5f));
		new Platform(new Vector2(0, -2));
		new CameraController(new Player(new Vector2(0, 0.5f)), window);
		//new Map();

		for (Script script : GameObject.scripts) script.Start();

		lastFPS = window.time.getTime();
	}

	@Override
	public void run() {
		init();		
		
		while (!window.shouldClose() && !window.input.isKeyDown(GLFW.GLFW_KEY_GRAVE_ACCENT)) {
			update();
			render();
		}
		
		close();
	}

	long lastFPS, fps = 0;
	private void update() {
		if (window.input.isKeyPressed(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
		
		for (Script object : GameObject.scripts) object.Update();
		
		window.update();
	}
	
	private void render() {
		shader.start();

		List<List<SpriteRenderer>> layers = new ArrayList<>();
		
		for (SpriteRenderer sr : SpriteRenderer.all) {
			while (sr.layer > layers.size() || sr.layer == layers.size()) layers.add(new ArrayList<>());
			
			layers.get(sr.layer).add(sr);
		}

		layers.forEach(layer -> layer.forEach(sr -> sr.render()));

		shader.stop();

		if (window.time.getTime() - lastFPS > 1000) {
			window.setTitle("FPS: " + fps);
			fps = 0; //reset the FPS counter
			lastFPS += 1000; //add one second
		}
		fps++;

		window.swapBuffers();
	}
	
	private void close() {
		shader.cleanUp();		
		for (GameObject obj : GameObject.all) obj.spriteRenderer.cleanUp();
		window.cleanUp();
	}
	
	public static void main(String[] args) {
		instance = new Main();
		instance.start();
	}
}
