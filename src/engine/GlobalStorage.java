package engine;

import engine.io.Window;
import engine.objects.GameObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Holds global static items.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class GlobalStorage {
    public static Collection<Script> scripts = new ArrayList<>();
    public static Collection<GameObject.SpriteRenderer> spriteRenders = new ArrayList<>();
    public static Window CurrentWindow;
}
