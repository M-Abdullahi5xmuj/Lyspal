package com.lyspal.flappy.level;

import com.lyspal.flappy.graphics.Shader;
import com.lyspal.flappy.graphics.VertexArray;

/**
 * Implements a level in the game.
 * 
 * @author sylvainlaporte
 *
 */
class Level {

	private VertexArray background;
	
	public Level() {
		
		// Create vertices.
		
		float[] vertices = new float[] {
			-10.0f, -10.0f * 9.0f /16,0f, 0.0f,		// (0) bottom-left corner
			-10.0f,  10.0f * 9.0f /16,0f, 0.0f,		// (1) top-left corner
			  0.0f,  10.0f * 9.0f /16,0f, 0.0f,		// (2) top-right corner
			  0.0f, -10.0f * 9.0f /16,0f, 0.0f		// (3) bottom-right corner
		};
		
		// Create triangle indices.
		// Indices are useful to avoid redundancy when defining triangles.
		// They are the indices of the vertices array.
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		// Create texture coordinate system.
		
		float[] tcs = new float[] {
			0, 1,
			0, 0,
			1, 0,
			1, 1
		};
		
		background = new VertexArray(vertices, indices, tcs);
	}
	
	public void render() {
		
		// Test rendering without a texture.
		
		Shader.BG.enable();
		background.render();
		Shader.BG.disable();
	}
	
}
