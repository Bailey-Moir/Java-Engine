package engine.maths;

/**
 * Manages time.
 * 
 * @author Bailey
 */

public class Time {
	
	public double deltaTime, deltaTimeSec;
	
	private long last_time, last_time_sec;
	
	/**
	 * Updates the delta time variable. To be run every frame.
	 */
	public void update() {
		long timeSec = System.currentTimeMillis() * 1000;
		long time = System.nanoTime();
		deltaTime = ((timeSec - last_time_sec) / 1000000);
		deltaTime = ((time - last_time) / 1000000);
		
		
		last_time = System.nanoTime();		   
		last_time_sec = System.currentTimeMillis() * 1000;
	}
}
