package engine.io;

import engine.Camera;
import engine.Script;
import engine.graphics.shaders.DefaultShader;
import engine.maths.Vector2;
import engine.maths.Vector3;
import engine.maths.Vector4;
import engine.objects.GameObject;
import engine.objects.components.SpriteRenderer;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * For a computer window.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Window {
	public static Window current;
	public static Camera activeCamera;
	
	private int WIDTH, HEIGHT,
				windowPosX, windowPosY;
	private final String TITLE;
	private float bgR, bgG, bgB;

	private long lastFPS = 0,
				 fpsCounter = 0,
				 fps = 0;
	
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
		current = this;
		
		if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		
		time = new Time();
		input = new Input(this);
		
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
		
		DefaultShader.instance = new DefaultShader();
		
		lastFPS = time.getTime();
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
		GL30.glClearColor(bgR, bgG, bgB, 1);
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT); //Or bit
		
		if (input.isKeyPressed(GLFW.GLFW_KEY_F11)) setFullscreen(!isFullscreen);

		for (Script object : GameObject.scripts) object.Update();
		
		time.update();
		
		if (time.getTime() - lastFPS > 1000) {
			fps = fpsCounter;
			fpsCounter = 0;
			lastFPS = time.getTime();
		}
		fpsCounter++;
		
		// RENDERING
		
		DefaultShader.instance.start();

		Collections.sort(SpriteRenderer.all);
		SpriteRenderer.all.forEach(SpriteRenderer::render);

		DefaultShader.instance.stop();

		GLFW.glfwSwapBuffers(window); //Swaps the buffers of the window, and colours it correctly.
		
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
	 * Destroys the window
	 */
	public void cleanUp() {
		input.destroy();
		sizeCallback.free();
		DefaultShader.instance.cleanUp();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
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
	
	// GETTERS & SETTERS
	
	/**
	 * @return Whether the window is full screened.
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
	 * @return the current frames per second of the window.
	 */
	public long getFps() {
		return fps;
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
	
	/**
	 * Sets the window color.
	 * @param color The color to set the window to.
	 */
	public void setBackgroundColour(Vector3 color) {
		bgR = color.x;
		bgG = color.y;
		bgB = color.z;
	}
	

	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
	}
}
