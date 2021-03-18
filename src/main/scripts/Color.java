package main.scripts;

import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;

public class Color extends GameObject {
    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public Color(Vector position, Vector size, Vector color, int layer) {
        super(Main.window, position, size, color, layer, "block");
    }

    public void Start() {}

    public void Update() {}
}