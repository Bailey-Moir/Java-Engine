package main.scripts;

import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;

public class Block extends GameObject {
    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public Block(Vector position, Vector size, Vector color, int layer) {
        super(position, size, Main.window, color, layer, "block");
    }
}