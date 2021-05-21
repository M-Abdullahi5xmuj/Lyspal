package com.lyspal.flappy.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.lyspal.flappy.math.Matrix4f;
import com.lyspal.flappy.math.Vector3f;
import com.lyspal.flappy.utils.ShaderUtils;

/**
 * A shader.
 */
public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader BG, BIRD, PIPE, FADE;		// the shaders
	
	/**
	 * Create and load every shaders for the game.
	 */
	public static void loadAll() {
		BG = new Shader("shaders/bg.vert", "shaders/bg.frag");
		BIRD = new Shader("shaders/bird.vert", "shaders/bird.frag");
		PIPE = new Shader("shaders/pipe.vert", "shaders/pipe.frag");
		FADE = new Shader("shaders/fade.vert", "shaders/fade.frag");
	}
	
	private boolean enabled = false;
	private final int ID;
	
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	/**
	 * Constructor.
	 * 
	 * @param vertex	the path to the vertex shader
	 * @param fragment	the path to the fragment shader
	 */
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	/**
	 * Uniform variables
	 * 
	 * Uniform variables provides data to the GPU from the CPU.
	 */
	
	/**
	 * Get a uniform variable by its name.
	 * 
	 * @param name		the name of the uniform variable
	 * @return			the address of thee uniform variable
	 */
	public int getUniform(String name) {
		// Return existing location if it already exists in cache.
		if (locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		// Otherwise, get a new uniform location, put it in cache and return it.
		int result = glGetUniformLocation(ID, name);		
		if (result == -1) {
			System.err.println("Could not find uniform variable '" + name + "'!");
		} else {
			locationCache.put(name, result);
		}
		return result;
	}
		
	/**
	 * Sets a 1 int uniform variable.
	 * 
	 * @param name		the name of the uniform variable
	 * @param value		the value of the int
	 */
	public void setUniform1i(String name, int value) {
		if (!enabled) enable();
		glUniform1i(getUniform(name), value);
	}
	
	/**
	 * Sets a 1 float uniform variable.
	 * 
	 * @param name		the name of the uniform variable
	 * @param value		the value tof the float
	 */
	public void setUniform1f(String name, float value) {
		if (!enabled) enable();
		glUniform1f(getUniform(name), value);
	}
	
	/**
	 * Sets a 2 float uniform variable.
	 * 
	 * @param name		the name of the uniform variable
	 * @param x			the value of the first float
	 * @param y			the value of the second float
	 */
	public void setUniform2f(String name, float x, float y) {
		if (!enabled) enable();
		glUniform2f(getUniform(name), x, y);
	}
	
	/**
	 * Sets a 3 float uniform variable.
	 * 
	 * @param name		the name of the uniform variable
	 * @param x			the value of the first float
	 * @param y			the value of the second float
	 */
	public void setUniform3f(String name, Vector3f vector) {
		if (!enabled) enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	/**
	 * Sets a 4 x 4 float matrix uniform variable.
	 * 
	 * @param name		the name of the uniform variable
	 * @param matrix	the value of the matrix
	 */
	public void setUniformMat4f(String name, Matrix4f matrix) {
		if (!enabled) enable();
		// Modified method for LWJGL3.
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
			// transpose is false because the matrices are column major.
	}
		
	/**
	 * Enables a shader.
	 */
	public void enable() {
		glUseProgram(ID);
		enabled = true;
	}
	
	/**
	 * Disables a shader.
	 */
	public void disable() {
		glUseProgram(0);
		enabled = false;
	}
	
}
