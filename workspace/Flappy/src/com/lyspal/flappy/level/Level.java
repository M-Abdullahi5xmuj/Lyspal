package com.lyspal.flappy.level;

import com.lyspal.flappy.graphics.Shader;
import com.lyspal.flappy.graphics.Texture;
import com.lyspal.flappy.graphics.VertexArray;
import com.lyspal.flappy.math.Matrix4f;
import com.lyspal.flappy.math.Vector3f;

/**
 * Implements a level in the game.
 * 
 * @author sylvainlaporte
 *
 */
public class Level {

	private VertexArray background;
	private Texture bgTexture;
	
	// Scrolling background.
	private int xScroll = 0;			// horizontal scroll for the bg.
	private int map = 0;
	
	private Bird bird;
	
	public Level() {
		
		/*
		 * Background
		 */
		
		// Create vertices.
		
		float[] vertices = new float[] {
			-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,		// (0) bottom-left corner
			-10.0f,  10.0f * 9.0f / 16.0f, 0.0f,		// (1) top-left corner
			  0.0f,  10.0f * 9.0f / 16.0f, 0.0f,		// (2) top-right corner
			  0.0f, -10.0f * 9.0f / 16.0f, 0.0f		// (3) bottom-right corner
		};
		
		// Create triangle with indices.
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
		bgTexture = new Texture("res/bg.jpeg");
		
		// Create a bird.
		bird = new Bird();
	}
	
	public void update() {
		// Background scrolling
		xScroll--;		// Scolling negative because we are moving map to the left.
		if (-xScroll % 335 == 0) map++;
		
		bird.update();
	}
	
	public void render() {
		bgTexture.bind();
		Shader.BG.enable();
		background.bind();		// Bind the background once.
		
		// Position at which the background map is rendered.
		for (int i = map; i < map + 4; i++) {
			Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
			background.draw();
		}
		Shader.BG.disable();
		bgTexture.unbind();
		
		bird.render();
	}
	
}
