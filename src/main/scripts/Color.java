package main.scripts;

import engine.maths.Vector2;
import engine.maths.Vector4;
import engine.objects.GameObject;

public class Color extends GameObject {
    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public Color(Vector2 position, Vector2 size, Vector4 color, int layer) {
        super(position, size, color, layer, "block");
    }
}