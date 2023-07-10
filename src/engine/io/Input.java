package engine.io;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import engine.maths.Vector2;

/**
 * Manages input, i.e. mouse position, mouse buttons, scroll, and keyboard keys.
 */
public class Input {
	//Turn buttons to int and make it 0-2
	private record Axis(String name, int[] keys) {}
	
	private final int[] keys = new int[GLFW.GLFW_KEY_LAST+1]; //The int length is the amount of keys
	private final int[] buttons = new int[GLFW.GLFW_MOUSE_BUTTON_LAST]; //The boolean length is the amount of mouse buttons
	private Vector2 mousePos = Vector2.zero();
	private Vector2 scroll = Vector2.zero();

	private final ArrayList<Axis> axes = new ArrayList<>();

	private final GLFWKeyCallback keyboard;
	private final GLFWCursorPosCallback mouseMove;
	private final GLFWMouseButtonCallback mouseButtons;
	private final GLFWScrollCallback mouseScroll;
	
	public final Window window;

	/**
	 * The default constructor, sets input callback functions.
	 */
	public Input(Window window) {
		this.window = window;
		
		keyboard = new GLFWKeyCallback() {
			public void invoke(long glfwWindow, int key, int scanCode, int action, int mods) {
				if (key > 0) keys[key] = action;
			}
		};

		mouseMove = new GLFWCursorPosCallback() {
			public void invoke(long glfwWindow, double x, double y) {
				mousePos = new Vector2((float) x - window.getWIDTH() / 2f, (float) y - window.getHEIGHT() / 2f);
			}
		};

		mouseButtons = new GLFWMouseButtonCallback() {
			public void invoke(long glfwWindow, int button, int action, int mods) {
				buttons[button] = action; //Different to Key because action spectrum is smaller.
			}
		};

		mouseScroll = new GLFWScrollCallback() {
			public void invoke(long glfwWindow, double offsetX, double offsetY) {
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
		return (this.keys[key] > 0); //2 = Held down, 1 == Pressed
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
			return (this.buttons[button] > 0);
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
		return (this.buttons[button] == 1);
	}

	/**
	 * Gives you the value of an axis.
	 * @exception StringIndexOutOfBoundsException If the axis you named doesn't exist.
	 * @exception IndexOutOfBoundsException If the number of keys in the given axis are odd.
	 * @param a_axis The name of the axis.
	 * @return The value of the axis.
	 */
	public float getAxisRaw(String a_axis) {
		for (Axis axis : axes) {
			if (axis.name.equals(a_axis)) {
				int pos = 0, neg = 0;
				for (int i = 0; i < axis.keys.length; i += 2) {
					if (this.isKeyDown(axis.keys[i + 1])) pos = 1;
					if (this.isKeyDown(axis.keys[i])) neg = 1;
				}
				return pos - neg;
			}
		}
		new StringIndexOutOfBoundsException("\"" + a_axis + "\" is not a valid axis.").printStackTrace();
		return 0;
	}

	/**
	 * Creates an axis that can be used anywhere in the code.
	 * @param name The name that you will refer to the axis by, e.g. "Horizontal".
	 * @param keys The two keys that make up the axis, the first being the negative, second being positive, e.g. {GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D}.
	 */
	public void createAxis(String name, int[] keys) {
		Axis localAxis = new Axis(name, keys);
		if (localAxis.keys.length % 2 == 1) throw new IndexOutOfBoundsException("Odd number of keys given.");
		axes.add(localAxis);
	}

	//Getters and Setters

	/**
	 * @return the position of the mouse relative to the window.
	 */
	public Vector2 getMousePos() {
		return mousePos;
	}

	/**
	 * @return the position of the mouse relative to the window.
	 */
	public float getMouseX() {
		return mousePos.x;
	}

	/**
	 * @return the position of the mouse relative to the window.
	 */
	public float getMouseY() {
		return mousePos.y;
	}

	/**
	 * @return the scroll offset relative to that at window creation.
	 */
	public Vector2 getScroll() {
		return scroll;
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
