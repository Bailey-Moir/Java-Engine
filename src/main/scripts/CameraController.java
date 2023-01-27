package main.scripts;

import engine.Camera;
import engine.Script;
import engine.io.Window;
import engine.maths.Vector2;
import engine.objects.GameObject;

import org.lwjgl.glfw.GLFW;

/**
 * Controls the Camera
 *
 * @author Bailey
 */

public class CameraController extends Camera implements Script {
    private final Player player;

    public CameraController(Player player, Window window) {
        super(Vector2.zero(), 1.5f);
        GameObject.scripts.add(this);
        this.player = player;
    }

    public void Start() {
        Window.activeCamera = this;
        scale = 2.5f;
    }

    public void Update() {
        if (window.input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            position = Vector2.zero();
        }

        //camPos = camPos + (plyPos - camPos) * modifier
        position = position._plus(player.transform.position._minus(position)._times(1f / 10f)); //10 is moveSpeed
        //scale = 3f - abs(position.minus(player.transform.position).largest() / 4);
    }
}
