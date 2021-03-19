package main;

import engine.Script;
import engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;

/**
 * The class for the updating thread
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class UpdateThread implements Runnable {
    public void init() throws InterruptedException {
        while (Main.instance.window.getWindow() == 0) {
            Thread.sleep(100);
        }
        GLFW.glfwMakeContextCurrent(Main.instance.window.getWindow());
    }

    @Override
    public void run() {
        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!Main.instance.window.shouldClose() && !Main.instance.window.input.isKeyDown(GLFW.GLFW_KEY_GRAVE_ACCENT)) {
            if (Main.instance.window.input.isKeyPressed(GLFW.GLFW_KEY_F11)) Main.instance.window.setFullscreen(!Main.instance.window.isFullscreen());

            for (Script object : GameObject.scripts) {
                object.Update();
            }

            Main.instance.window.time.update();
        }
    }
}
