package main;

import engine.Script;
import engine.graphics.Loader;
import engine.graphics.models.RawModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.shaders.StaticShader;
import engine.graphics.textures.ModelTexture;
import engine.maths.Vector;
import main.scripts.CameraController;
import main.scripts.Gate;
import org.lwjgl.glfw.GLFW;

import engine.GameObject;
import engine.graphics.Renderer;
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

	StaticShader shader;
	
	final int WIDTH = 2560, HEIGHT = 1440;
	final String TITLE = "big boy";
	Vector background = new Vector(new float[]{0.1f, 0.1f, 0.12f});

	private void start() {
		game = new Thread(this, "game");
		game.start(); //Runs the runs function, using Runnable
	}
	
	private void init() {
		window = new Window(WIDTH, HEIGHT, TITLE);
		window.setBackgroundColour(background);
		window.init();

		shader = new StaticShader();
		
		window.input.createAxis("Horizontal", new int[] {
				GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D,
				GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_RIGHT
		});
		window.input.createAxis("Vertical", new int[] {
				GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_W,
				GLFW.GLFW_KEY_DOWN, GLFW.GLFW_KEY_UP
		});

		new Gate(new Vector(new float[]{4.5f, 0.75f}), new Vector(new float[]{1, 1.5f}));
		new TempPlatform(new Vector(new float[]{-4, -2.5f}), new Vector(new float[]{3, 1}));
		new TempPlatform(new Vector(new float[]{0, -2f}), new Vector(new float[]{3, 1}));
		new TempPlatform(new Vector(new float[]{4, -1.5f}), new Vector(new float[]{2, 1}));
		new CameraController(new Player(new Vector(new float[]{0, -1})), window);

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
		shader.start();
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};

		for (GameObject object : GameObject.allObjects) {
			RawModel model = Loader.loadToVAO(object.transform.calculateVertices(Renderer.camera), object.spriteRenderer.getTextureCoords(), object.spriteRenderer.getColorCoords(), indices);
			ModelTexture texture = new ModelTexture(Loader.loadTexture(object.spriteRenderer.sprite.image));
			TexturedModel texturedModel = new TexturedModel(model, texture);

			Renderer.render(texturedModel);
		}

		shader.stop();

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
