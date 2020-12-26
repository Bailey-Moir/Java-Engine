package main.scripts;

import engine.GameObject;
import engine.Script;
import engine.components.*;
import engine.maths.Vector;
import main.Main;

public class Gate extends GameObject implements Script {
    private Collider col;
    private Rigidbody rb;

    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public Gate(Vector position, Vector size, Vector color) {
        super(position, size, Main.window, color, "icon16");
        GameObject.allScripts.add(this);
    }

    /**
     * Runs once when the window is created.
     */
    public void Start() {
        rb = new Rigidbody(this);
        col = new Collider(this, rb, true, true);
    }

    public void Update() {
        rb.update();
        col.update();

        if (col.isColliding) {
            spriteRenderer.getWindow().close();
        }
    }
}
