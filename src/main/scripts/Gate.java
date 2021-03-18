package main.scripts;

import engine.objects.GameObject;
import engine.Script;
import engine.objects.components.*;
import engine.maths.Vector;
import main.Main;

public class Gate extends GameObject {
    /**
     * The constructor.
     * The only thing you should touch in here are the variables in super().
     */
    public Gate(Vector position) {
        super(Main.window, position, new Vector(new float[]{2, 4}), new Vector(new float[]{1, 1, 1, 1}), 3, "portal");
    }

    public void Start() {}

    public void Update() {}
}
