/**
 * 
 */
package com.lyspal.flappy.graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * Utility methods for debugging OpenGL.
 * 
 * @author sylvainlaporte
 */
public class GLDebug {

	/**
	 * Clears all errors from OpenGL.
	 */
	public static void GLClearError() {
		while (glGetError() != GL_NO_ERROR) {}
	}
	
	/**
	 * Prints all error codes.
	 */
	public static void GLCheckError() {
		int error;
		while ((error = glGetError()) != GL_NO_ERROR) {
			System.out.println(error);
		}
	}
}
