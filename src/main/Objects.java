package main;

import java.util.*;

import engine.GameObject;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

/**
 * Contains all GameObjects. This CLASS MUST EXIST, works in tandem with the engine.
 * 
 * @author Bailey
 */
public class Objects extends Main {
	public static List<GameObject> li = new ArrayList<GameObject>();
	
	public static GameObject block3 = new GameObject(new Vector2f(-3, -1.5), new Vector2f(2, 1), window, new Vector3f(1, 0, 0), "null");
	public static GameObject block = new GameObject(new Vector2f(0, -3), new Vector2f(2, 1), window, new Vector3f(0, 1, 0), "null");
	public static GameObject block2 = new GameObject(new Vector2f(3, -1.5), new Vector2f(2, 1), window, new Vector3f(0, 0, 1), "null");
	
	public static GameObject player = new GameObject(new Vector2f(0, 0), new Vector2f(1, 1), window, new Vector3f(1, 1, 1), "player");
	
	public static void update() {
		for (GameObject object : li) {
			object.update();
		}
	}
}