package com.lyspal.flappy.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * Utilitary class containing methods to process user inputs.
 * 
 * @author sylvainlaporte
 */
public class Input extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[65536];	// 65536 is the max nb of values possible.
	
	/**
	 * Checks if a key is down.
	 * 
	 * @param keycode	the code of the key being pressed down.
	 * @return			true if the key is down; false otherwise
	 */
	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
	
	/**
	 * 
	 */
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW.GLFW_RELEASE;
	}

}
