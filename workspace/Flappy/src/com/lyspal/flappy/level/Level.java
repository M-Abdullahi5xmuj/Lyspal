package com.lyspal.flappy.level;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Random;

import com.lyspal.flappy.graphics.Shader;
import com.lyspal.flappy.graphics.Texture;
import com.lyspal.flappy.graphics.VertexArray;
import com.lyspal.flappy.input.Input;
import com.lyspal.flappy.math.Matrix4f;
import com.lyspal.flappy.math.Vector3f;

/**
 * A game level.
 * 
 * @author sylvainlaporte
 */
public class Level {

	private VertexArray background, fade;
	private Texture bgTexture;
	
	// To scroll the background.
	private int xScroll = 0;	// horizontal scroll for the bg.
	private int map = 0;
	
	private Bird bird;
	private Pipe[] pipes = new Pipe[5 * 2];
	private int pipeIndex = 0;
	private float offset = 5.0f;
	private boolean control = true, reset = false;
	
	private Random random = new Random();
	
	private float time = 0.0f;
	
	/**
	 * Constructor.
	 */
	public Level() {
		// Create the background vertices.
		float[] vertices = new float[] {
			//  X ,            Y         ,   Z
			-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,	// (0) bottom-left corner
			-10.0f,  10.0f * 9.0f / 16.0f, 0.0f,	// (1) top-left corner
			  0.0f,  10.0f * 9.0f / 16.0f, 0.0f,	// (2) top-right corner
			  0.0f, -10.0f * 9.0f / 16.0f, 0.0f		// (3) bottom-right corner
		};
		
		// Create triangle with indices for the background.
		// Indices are useful to avoid redundancy when defining triangles, since
		// they are the indices of the vertices array.
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		// Create texture coordinate system for the background.
		float[] tcs = new float[] {
			0, 1,
			0, 0,
			1, 0,
			1, 1
		};
		
		// Create level assets.
		fade = new VertexArray(6);
		background = new VertexArray(vertices, indices, tcs);
		bgTexture = new Texture("res/bg.jpeg");
		bird = new Bird();
		createPipes();
	}
	
	// Pipes
	
	/**
	 * Creates the pipes.
	 */
	private void createPipes() {
		Pipe.create();
		for (int i = 0; i < 5 * 2; i += 2) {
			// Top pipes.
			pipes[i] = new Pipe(offset + pipeIndex * 3.0f, random.nextFloat() * 4.0f);
			// Bottom pipes.
			pipes[i + 1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 12.5f);
			pipeIndex += 2;
		}
	}
	
	/**
	 * Updates the pipes.
	 */
	private void updatePipes() {
		pipes[pipeIndex % 10] = new Pipe(offset + pipeIndex * 3.0f, random.nextFloat() * 4.0f);
		pipes[(pipeIndex + 1) % 10] = new Pipe(pipes[pipeIndex % 10].getX(), pipes[pipeIndex % 10].getY() - 12.5f);
		pipeIndex += 2;
	}
	
	/**
	 * Renders the pipes.
	 */
	private void renderPipes() {
		Shader.PIPE.enable();
		// Set uniform variables for GPU rendering.
		Shader.PIPE.setUniform2f("bird", 0, bird.getY());
		Shader.PIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.05f, 0.0f, 0.0f)));
		// Bind the pipe mesh and texture.
		Pipe.getTexture().bind();
		Pipe.getMesh().bind();
		for (int i = 0; i < 5 * 2; i++) {
			Shader.PIPE.setUniformMat4f("ml_matrix", pipes[i].getModelMatrix());
			// Set a uniform variable for the top pipes.
			Shader.PIPE.setUniform1i("top", i % 2 == 0 ? 1 : 0);
			// Draw the pipes. 
			Pipe.getMesh().draw();
		}
		// Unbind the pipe mesh and texture.
		Pipe.getMesh().unbind();
		Pipe.getTexture().unbind();
	}
	
	/**
	 * Updates the level.
	 */
	public void update() {
		// Stop scrolling if collision.
		if (control) {
			// Background scrolling.
			// Scolling is negative because we are moving map to the left.
			xScroll--;
			if (-xScroll % 335 == 0) {
				map++;
			}
			// Scroll pipes.
			if (-xScroll > 250 && -xScroll % 120 == 0) {
				updatePipes();
			}
		}
		
		bird.update();
		
		if (control && collision()) {
			bird.fall();
			control = false;
		}
		
		if (!control && Input.isKeyDown(GLFW_KEY_SPACE)) {
			reset = true;
		}
		
		time += 0.01f;
	}
	
	/**
	 * Detects collisions between the bird and the pipes.
	 * 
	 * @return	true if collision; false otherwise
	 */
	private boolean collision() {
		for (int i = 0; i < 5 * 2; i++) {
			float bx = -xScroll * 0.05f;
			float by = bird.getY();
			float px = pipes[i].getX();
			float py = pipes[i].getY();
			
			float bx0 = bx - bird.getSize() / 2.0f;
			float bx1 = bx + bird.getSize() / 2.0f;
			float by0 = by - bird.getSize() / 2.0f;
			float by1 = by + bird.getSize() / 2.0f;

			float px0 = px;
			float px1 = px + Pipe.getWidth();
			float py0 = py;
			float py1 = py + Pipe.getHeight();
			
			if (bx1 > px0 && bx0 < px1) {
				if (by1 > py0 && by0 < py1) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the game is over.
	 * 
	 * @return	true if the game is over; false otherwise
	 */
	public boolean isGameOver() {
		return reset;
	}
	
	/**
	 * Renders the level.
	 */
	public void render() {
		// Render the background
		bgTexture.bind();
		Shader.BG.enable();
		Shader.BG.setUniform2f("bird", 0, bird.getY());
		background.bind();
		// Repeat the background to cover the window.
		for (int i = map; i < map + 4; i++) {
			Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
			// Draw the background.
			background.draw();
		}
		// Unbind the background mesh and texture.
		Shader.BG.disable();
		bgTexture.unbind();
		
		// Render the pipes.
		renderPipes();
		
		// Render the bird.
		bird.render();
		
		// Render the fade.
		Shader.FADE.enable();
		Shader.FADE.setUniform1f("time", time);
		fade.render();
		Shader.FADE.disable();
	}
	
}
