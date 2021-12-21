package main;

import engine.GlobalStorage;
import engine.Script;
import engine.graphics.Loader;
import engine.graphics.TexturedModel;
import engine.graphics.shaders.StaticShader;
import engine.maths.Vector2;
import engine.maths.Vector4;
import org.lwjgl.glfw.GLFW;

import engine.objects.GameObject;
import engine.graphics.Renderer;
import engine.io.Window;
import main.scripts.*;

import java.util.ArrayList;
import java.util.List;

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
		GlobalStorage.CurrentWindow = window;
		window.setBackgroundColour(background);
		window.init();

		shader = new StaticShader();

		//new GateCover(new Vector(10f, 1.5f));
		new Platform(new Vector2(0, -2));
		new CameraController(new Player(new Vector2(0, 0.5f)), window);
		//new Map();

		for (Script script : GlobalStorage.scripts) {
			script.Start();
		}

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
				
		for (Script object : GlobalStorage.scripts) {
			object.Update();
		}
		
		window.update();
		window.time.update();
	}
	
	private void render() {
		shader.start();
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};

		List<List<GameObject.SpriteRenderer>> layers = new ArrayList<>();

		GlobalStorage.spriteRenders.forEach(sr -> {
			while (sr.layer > layers.size() || sr.layer == layers.size()) {
				layers.add(new ArrayList<>());
			}

			layers.get(sr.layer).add(sr);
		});

		layers.forEach(layer -> layer.forEach(sr -> {
				int vaoID = Loader.loadToVAO(sr.calculateVertices(), sr.calculateTextureCoords(), sr.calculateColorCoords(), indices);
				int textureID = Loader.loadTexture(sr.sprite.image);
				TexturedModel texturedModel = new TexturedModel(textureID, vaoID, indices.length);

				Renderer.render(texturedModel);
			})
		);

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
		window.destroy();
		Loader.clear();
		shader.cleanUp();
	}
	
	public static void main(String[] args) {
		instance = new Main();
		instance.start();
	}
}
