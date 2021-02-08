package engine.io;

import engine.maths.Vector;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import engine.maths.Time;

/**
 * For a computer window.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Window {
	private int WIDTH, HEIGHT;
	private int windowPosX, windowPosY;
	private final String TITLE;
	private float bgR, bgG, bgB;
	
	private long window;

	public boolean shouldClose;
	
	public Input input;
	public Time time;
	
	private GLFWWindowSizeCallback sizeCallback;
	private boolean isResized;
	private boolean isFullscreen;

	/**
	 * The default constructor.
	 * @param width The width of the window.
	 * @param height The height of the window.
	 * @param title The title of the window.
	 */
	public Window(int width, int height, String title) {		
		this.WIDTH = width;
		this.HEIGHT = height;
		this.TITLE = title;
	}
	
	/**
	 * Initializes (in a non-literal sense) the window.
	 */
	public void init() {
		if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		
		time = new Time();
		input = new Input(WIDTH, HEIGHT, TITLE);
		
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
		if (window == 0) throw new RuntimeException("Failed to create the GLFW window");
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		assert videoMode != null;
		windowPosX = (videoMode.width() - WIDTH) / 2;
		windowPosY = (videoMode.height() - HEIGHT) / 2;
		GLFW.glfwSetWindowPos(window, windowPosX, windowPosY);		
		GLFW.glfwMakeContextCurrent(window);

		GL.createCapabilities(); //Adds the ability to render to the window in
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		
		createCallbacks();

		input.createAxis("Horizontal", new int[] {
				GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D,
				GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_RIGHT
		});
		input.createAxis("Vertical", new int[] {
				GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_W,
				GLFW.GLFW_KEY_DOWN, GLFW.GLFW_KEY_UP
		});

		GLFW.glfwShowWindow(window);
		
		GLFW.glfwSwapInterval(1); //Limits to 60fps
	}

	/**
	 * Creates/initializes the callbacks.
	 */
	private void createCallbacks() {
		sizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long window, int w, int h) {
				WIDTH = w;
				HEIGHT = h;
				
				isResized = true;
			}			
		};
		
		GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
		GLFW.glfwSetScrollCallback(window, input.getScrollCallback());
		GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
	}
	
	/**
	 * Sets the window color.
	 * @param color The color to set the window to.
	 */
	public void setBackgroundColour(Vector color) {
		bgR = color.getAxis(0);
		bgG = color.getAxis(1);
		bgB = color.getAxis(2);
	}
	
	/**
	 * Updates the window. Should be run every frame.
	 */
	public void update() {
		if (isResized) {
			GL30.glViewport(0, 0, WIDTH, HEIGHT);
			isResized = false;
		}
		//Clears the screen and sets the background color
		GL30.glClearColor(bgR, bgG, bgB, 1f);
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT); //Or bit
		
		GLFW.glfwPollEvents(); //Gets all the callbacks connected to the window.
	}
	
	/**
	 * @return If the window should close.
	 */
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window) || shouldClose;
	}

	/**
	 * Closes the window.
	 */
	public void close() {
		//GLFW.glfwSetWindowShouldClose(window, true);
		shouldClose = true;
	}

	/**
	 * Swaps the buffers of window, you should know what a buffer is.
	 */
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window); //Swaps the buffers of the window, and colours it correctly.
	}
	
	/**
	 * Destroys the window
	 */
	public void destroy() {
		input.destroy();
		sizeCallback.free();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}

	//Getters and Setters
	
	/**
	 * @return Whether or not the window is full screened.
	 */
	public boolean isFullscreen() {
		return isFullscreen;
	}

	/**
	 * @return the width of the window.
	 */
	public int getWIDTH() {
		return WIDTH;
	}

	/**
	 * @return the height of the window.
	 */
	public int getHEIGHT() {
		return HEIGHT;
	}

	/**
	 * @return the title of the window.
	 */
	public String getTITLE() {
		return TITLE;
	}

	/**
	 * @return the id of the window.
	 */
	public long getWindow() {
		return window;
	}	

	/**
	 * Sets if the window is fullscreen or not.
	 * @param isFullscreen un-fullscreen or to fullscreen.
	 */
	public void setFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		isResized = true;
		if (isFullscreen) {
			GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, WIDTH, HEIGHT, 0);
		} else {
			GLFW.glfwSetWindowMonitor(window, 0, windowPosX, windowPosY	, WIDTH, HEIGHT, 0);
		}
	}

}
