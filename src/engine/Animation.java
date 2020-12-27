package engine;

import engine.graphics.Sprite;

import java.util.ArrayList;
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
    private GameObject object;
    public Status status;

    /**
     * The constructor for an animation.
     * @param speed The speed that the animation plays at, default 1.
     * @param name The name to reference the animation by.
     * @param object The objeect that the animation acts on.
     * @param frames The list of frames in the animation.
     */
    public Animation(float speed, String name, GameObject object, boolean loop, Frame[] frames) {
        this.multiplier = speed;
        this.name = name;
        this.object = object;
        this.loop = loop;
        this.status = Status.STOPPED;

        for (Frame frame : frames) this.frames.add(frame);
    }

    /**
     * Plays the animation
     */
    public void play() {
        status = Status.PLAYING;
        //Creates new thread for this animation
        new Thread(new Runnable() {
            public void run()
            {
                long start = System.currentTimeMillis();
                frames.forEach((frame) -> {
                    try {
                        long diff = (long) start + (long) frame.time - System.currentTimeMillis();
                        if (diff > 0) Thread.sleep(diff);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    object.spriteRenderer.sprite = frame.sprite;
                });
                status = Status.STOPPED;
            }
        }).start();
    }
}
