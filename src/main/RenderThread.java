package main;

import engine.Script;
import engine.graphics.Loader;
import engine.graphics.models.RawModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.shaders.StaticShader;
import engine.graphics.textures.ModelTexture;
import engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

/**
 * The class for the rendering thread
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class RenderThread implements Runnable {
    private long lastFPS, fps = 0;

    StaticShader shader;

    public void init() {
        Main.instance.window.init();
        GLFW.glfwMakeContextCurrent(Main.instance.window.getWindow());
        shader = new StaticShader();

        for (Script script : GameObject.scripts) {
            script.StartRender();
        }
    }

    @Override
    public void run() {
        init();

        while (!Main.instance.window.shouldClose() && !Main.instance.window.input.isKeyDown(GLFW.GLFW_KEY_GRAVE_ACCENT)) {
            shader.start();
            int[] indices = {
                    0, 1, 3,
                    3, 1, 2
            };

            List<List<GameObject.SpriteRenderer>> layers = new ArrayList<>();

            GameObject.spriteRenders.forEach(sr -> {
                while (sr.layer > layers.size() || sr.layer == layers.size()) {
                    layers.add(new ArrayList<>());
                }

                layers.get(sr.layer).add(sr);
            });

            layers.forEach(layer -> { layer.forEach(sr -> {
                RawModel model = Loader.loadToVAO(sr.calculateVertices(), sr.calculateTextureCoords(), sr.calculateColorCoords(), indices);
                ModelTexture texture = new ModelTexture(Loader.loadTexture(sr.sprite.image));
                TexturedModel texturedModel = new TexturedModel(model, texture);

                engine.graphics.Renderer.render(texturedModel);
            });
            });

            shader.stop();

            if (Main.instance.window.time.getTime() - lastFPS > 1000) {
                Main.instance.window.setTitle("FPS: " + fps);
                fps = 0; //reset the FPS counter
                lastFPS += 1000; //add one second
            }
            fps++;

            Main.instance.window.update();
            Main.instance.window.swapBuffers();

            //close
            shader.cleanUp();
        }

        close();
    }

    public void close() {
        Main.instance.window.destroy();
    }
}
