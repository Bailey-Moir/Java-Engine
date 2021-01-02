package main.scripts;

import engine.Camera;
import engine.objects.GameObject;
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

    public CameraController(Player   player, Window window) {
        super(new Vector(new float[]{0, 0}), 1.5f, window);
        GameObject.allScripts.add(this);
        this.player = player;
    }

    public void Start() {
        Renderer.camera = this;
        scale = 2.5f;
    }

    public void Update() {
        if (window.input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            position = Vector.square(0, 2);
        }

        //camPos = camPos + (plyPos - camPos) * modifier
        position = position.plus(player.transform.position.minus(position).times(1f / 10f)); //10 is moveSpeed

    }
}
