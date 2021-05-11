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
	
	private Pipe[] pipes = new Pipe[5 * 2];
	private int index = 0;
		
	public Level() {
		
		/*
		 * Background
		 */
		
		// Create vertices.
		
		float[] vertices = new float[] {
			//  X ,            Y         ,   Z
			-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,		// (0) bottom-left corner
			-10.0f,  10.0f * 9.0f / 16.0f, 0.0f,		// (1) top-left corner
			  0.0f,  10.0f * 9.0f / 16.0f, 0.0f,		// (2) top-right corner
			  0.0f, -10.0f * 9.0f / 16.0f, 0.0f			// (3) bottom-right corner
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
		
		bird = new Bird();
		
		createPipes();
	}
	
	private void createPipes() {
		Pipe.create();
		for (int i = 0; i < 5 * 2; i += 2) {
			// Top pipes.
			pipes[i] = new Pipe(index * 3.0f, 4.0f);
			// Bottom pipes.
			pipes[i + 1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 10.0f);
			index += 2;
		}
	}
	
//	private void updatePipes() {
//		pipes[];
//	}
	
	public void update() {
		// Background scrolling.
		// Scolling negative because we are moving map to the left.
		xScroll--;
		if (-xScroll % 335 == 0) map++;
		
		bird.update();
	}
	
	private void renderPipes() {
		Shader.PIPE.enable();
		Shader.PIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.03f, 0.0f, 0.0f)));
		Pipe.getTexture().bind();
		Pipe.getMesh().bind();
		
		for (int i = 0; i < 5 * 2; i++) {
			Shader.PIPE.setUniformMat4f("ml_matrix", pipes[i].getModelMatrix());
//			Shader.PIPE.setUniform1i("top", i % 2 == 0 ? 1 : 0);
			Pipe.getMesh().draw();
		}
		
		Pipe.getMesh().unbind();
		Pipe.getTexture().unbind();
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
		
		renderPipes();
		bird.render();
	}
	
}
