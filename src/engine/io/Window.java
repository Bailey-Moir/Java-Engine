package engine.io;

import engine.Camera;
import engine.maths.Vector2;
import engine.maths.Vector4;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

/**
 * For a computer window.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Window {
	public static Window current;
	static public Camera activeCamera;
	
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
		current = this;
	}
	
	/**
	 * Initializes (in a non-literal sense) the window.
	 */
	public void init() {
		if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		
		time = new Time();
		input = new Input();
		
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
	public void setBackgroundColour(Vector4 color) {
		bgR = color.x;
		bgG = color.y;
		bgB = color.z;
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
		
		time.update();
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
	public void cleanUp() {
		input.destroy();
		sizeCallback.free();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}

	//Getters and Setters
	
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

	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
	}

	/**
	 * Manages input, i.e. mouse position, mouse buttons, scroll, and keyboard keys.
	 */
	@SuppressWarnings("unused")
	public class Input {
		//Turn buttons to int and make it 0-2

		private class Axis {
			/**
			 * The default constructor. Sets member variables.
			 * @param name The name that you will refer to the axis by, e.g. "Horizontal".
			 * @param keys The two keys that make up the axis, the first being the negative, second being positive, e.g. {GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D}.
			 */
			public Axis(String name, int[] keys) {
				this.name = name;
				this.keys = keys;
			}

			public String name;
			public int[] keys;
		}

		private final int[] keys = new int[GLFW.GLFW_KEY_LAST+1]; //The int length is the amount of keys
		private final int[] buttons = new int[GLFW.GLFW_MOUSE_BUTTON_LAST]; //The boolean length is the amount of mouse buttons
		private Vector2 mousePos;
		private Vector2 scroll;

		private final ArrayList<Axis> axes = new ArrayList<>();

		private final GLFWKeyCallback keyboard;
		private final GLFWCursorPosCallback mouseMove;
		private final GLFWMouseButtonCallback mouseButtons;
		private final GLFWScrollCallback mouseScroll;

		/**
		 * The default constructor, sets input callback functions.
		 */
		public Input() {
			keyboard = new GLFWKeyCallback() {
				public void invoke(long window, int key, int scanCode, int action, int mods) {
					if (key > 0) keys[key] = action;
				}
			};

			mouseMove = new GLFWCursorPosCallback() {
				public void invoke(long window, double x, double y) {
					mousePos = new Vector2((float) x - getWIDTH() / (float) 2, (float) y - getHEIGHT() / (float) 2);
				}
			};

			mouseButtons = new GLFWMouseButtonCallback() {
				public void invoke(long window, int button, int action, int mods) {
					buttons[button] = action; //Different to Key because action spectrum is smaller.
				}
			};

			mouseScroll = new GLFWScrollCallback() {
				public void invoke(long window, double offsetX, double offsetY) {
					scroll = new Vector2((float) offsetX, (float) offsetY);
				}
			};
		}

		/**
		 * Deletes the input callbacks.
		 */
		public void destroy() {
			keyboard.free();
			mouseMove.free();
			mouseButtons.free();
			mouseScroll.free();
		}

		/**
		 * Returns whether the specified key is down.
		 * @param key The integer of the key you are checking. E.g. 65, or more likely GLFW.GLFW_KEY_A.
		 * @return If the key is being held down or not.
		 */
		public boolean isKeyDown(int key) {
			return (this.keys[key] == 2 || this.keys[key] == 1); //2 = Held down, 1 == Pressed
		}

		/**
		 * Returns whether the specified key was just pressed.
		 * @param key The integer of the key you are checking. E.g. 65, or more likely GLFW.GLFW_KEY_A.
		 * @return If the key was just pressed or not.
		 */
		public boolean isKeyPressed(int key) {
			return (keys[key] == 1); //1 = Pressed down
		}

		/**
		 * Returns whether the specified mouse button is down.
		 * @param button The integer of the button you are checking. E.g. 1, or more likely GLFW.GLFW_MOUES_BUTTON_LEFT
		 * @return If the button is being held down or not.
		 */
		public boolean isButtonDown(int button) {
			try {
				return (this.buttons[button] == 2 || this.buttons[button] == 1);
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				return false;
			}
		}

		/**
		 * Returns whether the specified button was just pressed.
		 * @param button The integer of the button you are checking. E.g. 1, or more likely GLFW.GLFW_MOUES_BUTTON_LEFT
		 * @return If the button was just pressed or not.
		 */
		public boolean isButtonPressed(int button) {
			return this.buttons[button] == 1;
		}

		/**
		 * Gives you the value of an axis.
		 * @exception StringIndexOutOfBoundsException If the axis you named doesn't exist.
		 * @exception IndexOutOfBoundsException If the number of keys in the given axis are odd.
		 * @param a_axis The name of the axis.
		 * @return The value of the axis.
		 */
		public float getAxisRaw(String a_axis) {
			try {
				for (Axis axis : axes) {
					if (axis.keys.length % 2 != 0) {
						throw new IndexOutOfBoundsException("Odd number of keys given.");
					}

					if (axis.name.equals(a_axis)) {
						int positive = 0;
						int negative = 0;
						for (int i = 0; i < axis.keys.length; i += 2) {
							if (this.isKeyDown(axis.keys[i + 1])) {
								positive = 1;
							}
							if (this.isKeyDown(axis.keys[i])) {
								negative = 1;
							}
						}
						return positive - negative;
					}
				}
				throw new StringIndexOutOfBoundsException("\"" + a_axis + "\" is not a valid axis.");
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				return 0;
			}
		}

		/**
		 * Creates an axis that can be used anywhere in the code.
		 * @param name The name that you will refer to the axis by, e.g. "Horizontal".
		 * @param keys The two keys that make up the axis, the first being the negative, second being positive, e.g. {GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D}.
		 */
		public void createAxis(String name, int[] keys) {
			Axis localAxis = new Axis(name, keys);
			axes.add(localAxis);
		}

		//Getters and Setters

		/**
		 * @return the position of the mouse relative to the window.
		 */
		public Vector2 getMousePos() {
			return (mousePos ==  null ? Vector2.zero() : mousePos);
		}

		/**
		 * @return the position of the mouse relative to the window.
		 */
		public float getMouseX() {
			return (mousePos == null ? 0f : mousePos.x);
		}

		/**
		 * @return the position of the mouse relative to the window.
		 */
		public float getMouseY() {
			return (mousePos == null ? 0f : mousePos.y);
		}

		/**
		 * @return the scroll offset relative to that at window creation.
		 */
		public Vector2 getScroll() {
			return (scroll ==  null ? Vector2.zero() : scroll);
		}

		/**
		 * Returns the keyboard callback, mainly used to create callbacks within the window class.
		 * @return the keyboard callback.
		 */
		public GLFWKeyCallback getKeyboardCallback() {
			return keyboard;
		}

		/**
		 * Returns the mouse position callback, mainly used to create callbacks within the window class.
		 * @return the mouse position callback.
		 */
		public GLFWCursorPosCallback getMouseMoveCallback() {
			return mouseMove;
		}

		/**
		 * Returns the mouse button callback, mainly used to create callbacks within the window class.
		 * @return the mouse button callback.
		 */
		public GLFWMouseButtonCallback getMouseButtonsCallback() {
			return mouseButtons;
		}

		/**
		 * Returns the scroll callback, mainly used to create callbacks within the window class.
		 * @return the scroll callback.
		 */
		public GLFWScrollCallback getScrollCallback() {
			return mouseScroll;
		}
	}

	/**
	 * Manages time.
	 */
	@SuppressWarnings("unused")
	static public class Time {
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

}
