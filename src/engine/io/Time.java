package engine.io;

/**
 * Manages time.
 */
public class Time {
	public double deltaTime, deltaTimeSec;

	private long last_time, last_time_sec;

	public long getTime() {
		return System.currentTimeMillis();
	}

	/**
	 * Updates the delta time variable. To be run every frame.
	 */
	public void update() {
		long timeSec = System.currentTimeMillis() * 1000;
		long time = System.nanoTime();
		deltaTimeSec = ((timeSec - last_time_sec) / (float) 1000000);
		deltaTime = ((time - last_time) / (float) 1000000);


		last_time = System.nanoTime();
		last_time_sec = System.currentTimeMillis() * 1000;
	}
}