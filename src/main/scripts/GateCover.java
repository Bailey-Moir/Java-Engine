package main.scripts;

import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;
import org.lwjgl.glfw.GLFW;

public class GateCover extends GameObject {
    private Collider col;

    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public GateCover(Vector position) {
        super(Main.instance.window, position, new Vector(new float[]{1, 4}), new Vector(new float[]{1, 1, 1, 1}), 0, "portal");
    }

    /**
     * Runs once when the window is created.
     */
    @Override
    public void StartUpdate() {
        Rigidbody rb = new Rigidbody(this);
        col = new Collider(this, rb, true, true);

        col.size.setAxis(0, transform.size.getAxis(0) / 2);
        col.offset.setAxis(0, col.offset.getAxis(0) + transform.size.getAxis(0) / 2);
    }
    @Override
    public void StartRender() {
        spriteRenderer.sprite.texCords = new float[] {
                0.5f, 0,
                0.5f, 1,
                1, 1,
                1, 0
        };
    }
    @Override
    public void Update() {
        col.update();

        col.collidingWith.forEach((colL) -> {
            if (!colL.isStatic) {
                spriteRenderer.getWindow().close();
            }
        });
    }
}
