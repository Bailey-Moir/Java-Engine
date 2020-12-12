package engine.io;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import engine.maths.Vector2f;

/**
 * Manages input, i.e. mouse position, mouse buttons, scroll, and keyboard keys.
 * 
 * @author Bailey
 */

public class Input extends Window {
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
	
	private int[] keys = new int[GLFW.GLFW_KEY_LAST+1]; //The int length is the amount of keys
	private int[] buttons = new int[GLFW.GLFW_MOUSE_BUTTON_LAST]; //The boolean length is the amount of mouse buttons
	private Vector2f mousePos;
	private Vector2f scroll;
	
	private ArrayList<Axis> axes = new ArrayList<Axis>();
	
	private GLFWKeyCallback keyboard;
	private GLFWCursorPosCallback mouseMove;
	private GLFWMouseButtonCallback mouseButtons;
	private GLFWScrollCallback mouseScroll;
	
	/**
	 * The default constructor, sets input callback functions.
	 */
	public Input(int width, int height, String title) {
		super(width, height, title);

		keyboard = new GLFWKeyCallback() {
			public void invoke(long window, int key, int scanCode, int action, int mods) {
				keys[key] = action;
			}
		};
		
		mouseMove = new GLFWCursorPosCallback() {
			public void invoke(long window, double x, double y) {
				mousePos = new Vector2f((float) x - getWIDTH() / 2, (float) y - getHEIGHT() / 2);
			}
		};
		
		mouseButtons = new GLFWMouseButtonCallback() {
			public void invoke(long window, int button, int action, int mods) {
				buttons[button] = action; //Different to Key because action spectrum is smaller.
			}
		};
		
		mouseScroll = new GLFWScrollCallback() {
			public void invoke(long window, double offsetX, double offsetY) {
				scroll = new Vector2f((float) offsetX, (float)offsetY);
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
	 * Returns whether or not the specified key is down.
	 * @param key The integer of the key you are checking. E.g. 65, or more likely GLFW.GLFW_KEY_A.
	 * @return If the key is being held down or not.
	 */
	public boolean isKeyDown(int key) {
		return (this.keys[key] == 2 || this.keys[key] == 1); //2 = Held down, 1 == Pressed
	}
	
	/**
	 * Returns whether or not the specified key was just pressed.
	 * @param key The integer of the key you are checking. E.g. 65, or more likely GLFW.GLFW_KEY_A.
	 * @return If the key was just pressed or not.
	 */
	public boolean isKeyPressed(int key) {
		return (keys[key] == 1); //1 = Pressed down
	}
	
	/**
	 * Returns whether or not the specified mouse button is down.
	 * @param button The integer of the button you are checking. E.g. 1, or more likely GLFW.GLFW_MOUES_BUTTON_LEFT
	 * @return If the button is being held down or not.
	 */
	public boolean isButtonDown(int button) {
		return (this.buttons[button] == 2 || this.buttons[button] == 1);
	}
	
	/**
	 * Returns whether or not the specified button was just pressed.
	 * @param button The integer of the button you are checking. E.g. 1, or more likely GLFW.GLFW_MOUES_BUTTON_LEFT
	 * @return If the button was just pressed or not.
	 */
	public boolean isButtonPressed(int button) {
		return this.buttons[button] == 1;
	}
		
	/**
	 * Gives you the value of an axis.
	 * @exception If the axis you named doesn't exist.
	 * @param a_axis The name of the axis.
	 * @return The value of the axis.
	 */
	public float getAxisRaw(String a_axis) {
		try {
			for (Axis axis : axes) {
				if (axis.keys.length % 2 != 0) {
					throw new Exception("Odd number of keys given.");
				}
				
				if (axis.name == a_axis) {
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
			throw new Exception("\"" + a_axis + "\" is not a valid axis.");
		} catch (Exception e) {
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
	public Vector2f getMousePos() {
		return (mousePos ==  null ? new Vector2f(0, 0) : mousePos);
	}

	/**
	 * @return the scroll offset relative to that at window creation.
	 */
	public Vector2f getScroll() {
		return (scroll ==  null ? new Vector2f(0, 0) : scroll);
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
