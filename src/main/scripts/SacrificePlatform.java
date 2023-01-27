package main.scripts;

import engine.maths.Vector2;
import engine.maths.Vector4;
import engine.objects.GameObject;
import engine.objects.SpriteSheet;
import engine.objects.components.*;

public class SacrificePlatform extends GameObject {
    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public SacrificePlatform(Vector2 position) {
        super(position, new Vector2(0.5f), new Vector4(1), 1, "player");
    }

    /**
     * Runs once when the window is created.
     */
    public void Start() {
        spriteRenderer.spriteSheet = new SpriteSheet("player", new int[] {
                10, 20,
                1, 1
        });
        spriteRenderer.spriteSheet.gen();

        spriteRenderer.setSprite(spriteRenderer.spriteSheet.getSprite(0));
        new Collider(this, new Rigidbody(this), true, false);
    }

    public void Update() {}
}