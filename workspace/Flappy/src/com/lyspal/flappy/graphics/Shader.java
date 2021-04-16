package com.lyspal.flappy.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.lyspal.flappy.math.Matrix4f;
import com.lyspal.flappy.math.Vector3f;
import com.lyspal.flappy.utils.ShaderUtils;

/**
 * Repesent an actual shader.
 * 
 * @author sylvainlaporte
 *
 */
public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	// Uniform variables provides data to the GPU from the CPU.
	
	/**
	 * Get a uniform varible by its name.
	 * 
	 * @param name		name of the uniform variable
	 * @return			address of thee uniform variable
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
	
	// Methods for setting uniform variables.
	
	/**
	 * Set a 1 int uniform variable.
	 * 
	 * @param name		name of the uniform variable
	 * @param value		value to put in the uniform variable
	 */
	public void setUniform1i(String name, int value) {
		glUniform1i(getUniform(name), value);
	}
	
	/**
	 * Set a 1 float uniform variable.
	 * 
	 * @param name		name of the uniform variable
	 * @param value		value to put in the uniform variable
	 */
	public void setUniform1f(String name, float value) {
		glUniform1f(getUniform(name), value);
	}
	
	/**
	 * Set a 2 float uniform variable.
	 * 
	 * @param name		name of the uniform variable
	 * @param x			value of the first float
	 * @param y			value of the second float
	 */
	public void setUniform2f(String name, float x, float y) {
		glUniform2f(getUniform(name), x, y);
	}
	
	/**
	 * Set a 3 float uniform variable.
	 * 
	 * @param name		name of the uniform variable
	 * @param x			value of the first float
	 * @param y			value of the second float
	 */
	public void setUniform3f(String name, Vector3f vector) {
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
			// transpose = false because the matrices are column major.
	}
	
	// Methods for enabling and disabling the shader.
	
	public void enable() {
		glUseProgram(ID);
	}
	
	public void diable() {
		glUseProgram(0);
	}
	
}
