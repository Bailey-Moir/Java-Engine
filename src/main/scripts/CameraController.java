package main.scripts;

import engine.Camera;
import engine.GameObject;
import engine.Script;
import engine.graphics.Renderer;
import engine.io.Window;
import engine.maths.Vector;
import org.lwjgl.glfw.GLFW;

/**
 * Controls the Camera
 *
 * @author Bailey
 */

public class CameraController extends Camera implements Script {
    private final Player player;
    final float modifier = 10;

    public CameraController(Player player, Window window) {
        super(new Vector(new float[]{0, 0}), 1.5f, window);
        GameObject.allScripts.add(this);
        this.player = player;
    }

    public void Start() {
        Renderer.camera = this;
    }

    public void Update() {
        if (window.input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            position = new Vector(new float[]{0, 0});
        }

        //camPos = camPos + (plyPos - camPos) / modifier
        position = position.plus(player.transform.position.minus(position).times(1/ modifier));
    }
}
