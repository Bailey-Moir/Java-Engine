package main.scripts;

import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;

public class Gate extends GameObject implements Script {
    private Collider col;

    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public Gate(Vector position) {
        super(position, new Vector(new float[]{2, 4}), Main.window, new Vector(new float[]{1, 1, 1, 1}), 0, "null");
        GameObject.allScripts.add(this);
    }

    /**
     * Runs once when the window is created.
     */
    public void Start() {
        Rigidbody rb = new Rigidbody(this);
        col = new Collider(this, rb, true, true);
    }

    public void Update() {
        col.update();

        for (Collider colL : col.collidingWith) {
            if (!colL.isStatic) {
                spriteRenderer.getWindow().close();
            }
        }
    }
}
