package main.scripts;

import engine.maths.Vector2;
import engine.maths.Vector4;
import engine.objects.GameObject;
import engine.objects.components.*;

public class GateCover extends GameObject {
    private Collider col;

    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public GateCover(Vector2 position) {
        super(position, new Vector2(1, 4), new Vector4(1), 0, "block");
    }

    /**
     * Runs once when the window is created.
     */
    public void Start() {
        spriteRenderer.getSprite().setTextureCoordinates(new float[] {
                0.5f, 0,
                0.5f, 1,
                1, 1,
                1, 0
        });

        Rigidbody rb = new Rigidbody(this);
        col = new Collider(this, rb, true, true);

        col.size.x = transform.size.x / 2;
        col.offset.x = col.offset.x + transform.size.x / 2;
    }

    public void Update() {
        updateComponents();

        col.collidingWith.forEach(colL -> {
            if (!colL.isStatic) {
                spriteRenderer.getWindow().close();
            }
        });
    }
}
