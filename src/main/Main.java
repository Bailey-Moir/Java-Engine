package main;

import org.lwjgl.glfw.GLFW;

import engine.Script;
import engine.graphics.shaders.DefaultShader;
import engine.io.Window;
import engine.maths.Vector2;
import engine.maths.Vector3;
import engine.objects.GameObject;
import main.scripts.CameraController;
import main.scripts.GateCover;
import main.scripts.Map;
import main.scripts.Platform;
import main.scripts.Player;

/**
 * The starting off point.
 *
 * TODO Create lighting, e.g. light for the portal.
 *
 * @author Bailey
 */

public class Main {
	public static Window window;
	
	public static void main(String[] args) {
		init();		
		
		while (!window.shouldClose() && !window.input.isKeyDown(GLFW.GLFW_KEY_GRAVE_ACCENT)) {
			update();
		}
		
		close();
	}
	
	private static void init() {
		window = new Window(2000, 1500, "Debugging");
		window.setBackgroundColour(new Vector3(0.1f, 0.1f, 0.12f));

		new GateCover(new Vector2(10, 1.5f));
		new Platform(new Vector2(0, -2));
		new CameraController(new Player(new Vector2(0, 0.5f)), window);
		new Map();

		for (Script script : GameObject.scripts) script.Start();
	}
	
	private static void update() {				
		window.update();
		
		window.setTitle("Debugging - " + window.getFps());
	}
	
	private static void close() {
		for (GameObject obj : GameObject.all) obj.spriteRenderer.cleanUp();
		window.cleanUp();
	}
}
