package com.lyspal.flappy.utils;

import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {

	private ShaderUtils() {
	}
	
	/**
	 * Load the files containing the vert and frag informationé
	 * @param vertPath		path to the vert file
	 * @param fragPath		path to the frag file
	 * @return				an openGL program
	 */
	public static int load(String vertPath, String fragPath) {
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragPath);
		return create(vert, frag);
	}
	
	/**
	 * Create the program to run on the GPU (aka shaders).
	 * 
	 * @param vert
	 * @param frag
	 * @return
	 */
	public static int create(String vert, String frag) {
		int program = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);
		
		glCompileShader(vertID);
		if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID));
			return -1;
		}
		
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID));
			return -1;
		}
		
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
