package engine.maths;

/**
 * Manages time.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Time {
	public double deltaTime, deltaTimeSec;
	
	private long last_time, last_time_sec;
	
	/**
	 * Updates the delta time variable. To be run every frame.
	 */
	public void update() {
		long timeSec = System.currentTimeMillis() * 1000;
		long time = System.nanoTime();
		deltaTime = ((timeSec - last_time_sec) / (float) 1000000);
		deltaTime = ((time - last_time) / (float) 1000000);
		
		
		last_time = System.nanoTime();		   
		last_time_sec = System.currentTimeMillis() * 1000;
	}
}
