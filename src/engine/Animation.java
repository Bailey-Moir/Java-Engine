package engine;

import engine.objects.Sprite;
import engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * An animation
 *
 * @author Bailey
 */
public class Animation {
    /**
     * The different states of status.
     */
    public enum Status {
        PLAYING, STOPPED
    }
    /**
     * Represents a frame.
     */    
    public record Frame(Sprite sprite, float time) {}

    public List<Frame> frames = new ArrayList<>();

    public boolean loop;
    public String name;
    public Status status;
    public long speed = 1;

    private Thread animThread;

    /**
     * The constructor for an animation.
     * @param speed The speed that the animation plays at, default 1.
     * @param name The name to reference the animation by.
     * @param frames The list of frames in the animation.
     */
    public Animation(String name, boolean loop, Frame[] frames) {
        this.name = name;
        this.loop = loop;
        this.status = Status.STOPPED;

        this.frames.addAll(List.of(frames));
    }

    /**
     * Plays the animation
     * @param object The object to play the animation on.
     */
    public void play(GameObject object) {
        status = Status.PLAYING;
        //Creates new thread for this animation
        animThread = new Thread(() -> {
            long start = System.currentTimeMillis();
            for (Frame frame : frames) {
            	try {
                    long diff = start + (long) frame.time/speed - System.currentTimeMillis();
                    if (diff > 0) Thread.sleep(diff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                object.spriteRenderer.setSprite(frame.sprite);	
            }
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
