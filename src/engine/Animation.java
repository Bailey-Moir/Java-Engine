package engine;

import engine.objects.Sprite;
import engine.objects.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An animation
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Animation {
    /**
     * The different states of status.
     */
    public enum Status {
        PLAYING, STOPPED
    }
    /**
     * The class that represents a frame.
     */
    public static class Frame {
        public Frame(Sprite sprite, float time) {
            this.sprite = sprite;
            this.time = time;
        }

        public float time;
        public Sprite sprite;
    }

    public List<Frame> frames = new ArrayList<>();

    public float multiplier;
    public boolean loop;
    public String name;
    private final GameObject object;
    public Status status;

    private Thread animThread;

    /**
     * The constructor for an animation.
     * @param speed The speed that the animation plays at, default 1.
     * @param name The name to reference the animation by.
     * @param object The object that the animation acts on.
     * @param frames The list of frames in the animation.
     */
    public Animation(float speed, String name, GameObject object, boolean loop, Frame[] frames) {
        this.multiplier = speed;
        this.name = name;
        this.object = object;
        this.loop = loop;
        this.status = Status.STOPPED;

        Collections.addAll(this.frames, frames);
    }

    /**
     * Plays the animation
     */
    public void play() {
        status = Status.PLAYING;
        //Creates new thread for this animation
        animThread = new Thread(() -> {
            long start = System.currentTimeMillis();
            frames.forEach(frame -> {
                /*try {
                    long diff = start + (long) frame.time - System.currentTimeMillis();
                    if (diff > 0) Thread.sleep(diff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                object.spriteRenderer.sprite = frame.sprite;*/
            });
            status = Status.STOPPED;
        });
        animThread.start();
    }

    /**
     * Stops the animation, regardless of where it is through the animation;
     */
    public void stop() {
        if (animThread != null) animThread.stop();
    }
}
