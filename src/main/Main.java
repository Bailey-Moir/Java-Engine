package main;

import engine.Script;
import engine.graphics.Loader;
import engine.graphics.shaders.StaticShader;
import engine.maths.Vector;
import org.lwjgl.glfw.GLFW;

import engine.objects.GameObject;
import engine.io.Window;
import main.scripts.*;
import org.lwjglx.opengl.GLContext;

/**
 * The starting off point.
 *
 * TODO Create lighting, e.g. light for the portal.
 *
 * @author Bailey
 */

public class Main implements Runnable {
	static Thread game;
	
	public static Main instance;
	public volatile Window window;

	public StaticShader shader;
	
	final int WIDTH = 2560, HEIGHT = 1440;
	final String TITLE = "Engine";
	Vector background = new Vector(new float[]{0.1f, 0.1f, 0.12f, 1.0f});

	private void start() {
		game = new Thread(this, "game");
		game.start(); //Runs the runs function, using Runnable
	}
	
	private void init() {
		window = new Window(WIDTH, HEIGHT, TITLE);
		window.setBackgroundColour(background);

		new Gate(new Vector(new float[]{9.5f, 1.5f}));
		new GateCover(new Vector(new float[]{10f, 1.5f}));
		new Color(new Vector(new float[]{10.75f, 1.5f}), new Vector(new float[]{2, 4}), new Vector(background), 1);
		new Platform(new Vector(new float[]{-5.5f, -4f}));
		new Platform(new Vector(new float[]{0, -2f}));
		new LongPlatform(new Vector(new float[]{7.5f, -1.5f}));
		new CameraController(new Player(new Vector(new float[]{0, 0.5f})), window);

		for (Script script : GameObject.scripts) {
			script.StartUpdate();
		}

		//lastFPS = window.time.getTime();
	}

	@Override
	public void run() {
		init();		

		UpdateThread updater = new UpdateThread();
		RenderThread renderer = new RenderThread();

		Thread updateThread = new Thread(updater, "updating");
		Thread renderThread = new Thread(renderer, "rendering");

		updateThread.start();
		renderThread.start();

		close();
	}
	
	private void close() {
		Loader.clear();
	}
	
	public static void main(String[] args) {
		instance = new Main();
		instance.start();
	}
}
