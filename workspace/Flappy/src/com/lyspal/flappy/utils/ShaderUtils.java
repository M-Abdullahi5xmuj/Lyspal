package com.lyspal.flappy.utils;

import static org.lwjgl.opengl.GL20.*;

/**
 * Utility class for shaders.
 * 
 * @author sylvainlaporte
 */
public class ShaderUtils {

	/**
	 * Constructor.
	 */
	private ShaderUtils() {
		
	}
	
	/**
	 * Loads the files containing the vert and frag information.
	 * 
	 * @param vertPath		the path to the vert file
	 * @param fragPath		the path to the frag file
	 * @return				the resulting shader
	 */
	public static int load(String vertPath, String fragPath) {
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragPath);
		return create(vert, frag);
	}
	
	/**
	 * Creates a shader, a program to run on the GPU.
	 * 
	 * @param vert	the vertex shader source code
	 * @param frag	the fragment shader source code
	 * @return		the resulting shader
	 */
	public static int create(String vert, String frag) {
		int program = glCreateProgram();
		// Get shader IDs.
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		// Combine ID and sources.
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);
		
		// Compile the vertex shader.
		glCompileShader(vertID);
		if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID));
			return -1;
		}
		
		// Compile the fragment shader.
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID));
			return -1;
		}
		
		// Link the source to the program.
		glAttachShader(program, vertID);
		glAttachShader(program, fragID);
		glLinkProgram(program);
		glValidateProgram(program);
		
		// Delete the shaders which are now part of the program.
		glDeleteShader(vertID);
		glDeleteShader(fragID);
		
		return program;
	}
}
