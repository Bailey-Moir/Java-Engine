package engine.io;

import static org.lwjgl.glfw.GLFW.glfwInit;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import engine.maths.Time;
import engine.maths.Vector3f;

/**
 * For a computer window.
 * 
 * @author Bailey
 */

public class Window {
	
	private int WIDTH, HEIGHT;
	private int windowPosX, windowPosY;
	private String TITLE;
	private float bgR, bgG, bgB;
	
	private long window;
	
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
		if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		
		time = new Time();
		input = new Input(WIDTH, HEIGHT, TITLE);
		
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
		if (window == 0) throw new RuntimeException("Failed to create the GLFW window");
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		windowPosX = (videoMode.width() - WIDTH) / 2;
		windowPosY = (videoMode.height() - HEIGHT) / 2;
		GLFW.glfwSetWindowPos(window, windowPosX, windowPosY);		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities(); //Adds the ability to render to the window in 
		GL14.glEnable(GL11.GL_DEPTH_TEST);
		
		createCallbacks();
		
		GLFW.glfwShowWindow(window);
		
		GLFW.glfwSwapInterval(1); //Limts to 60fps
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
	public void setBackgroundColour(Vector3f color) {
		bgR = color.x;
		bgG = color.y;
		bgB = color.z;
	}
	
	/**
	 * Updates the window. Should be run every frame.
	 */
	public void update() {
		if (isResized) {
			GL14.glViewport(0, 0, WIDTH, HEIGHT);
			isResized = false;
		}
		//Clears the screen and sets the background color
		GL14.glClearColor(bgR, bgG, bgB, 1f);
		GL14.glClear(GL14.GL_COLOR_BUFFER_BIT | GL14.GL_DEPTH_BUFFER_BIT); //Or bit
		
		GLFW.glfwPollEvents(); //Gets all the callbacks connected to the window.
	}
	
	/**
	 * @return If the window should close.
	 */
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	/**
	 * Swaps the buffers of window, you should know what a buffer is.
	 */
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window); //Swaps the bufferes of the window, and colours it correctly.
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
	 * @param isFullscreenTo un-fullscreen or to fullscreen.
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
