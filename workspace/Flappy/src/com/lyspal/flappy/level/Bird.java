package com.lyspal.flappy.level;

import static org.lwjgl.glfw.GLFW.*;

import com.lyspal.flappy.graphics.Shader;
import com.lyspal.flappy.graphics.Texture;
import com.lyspal.flappy.graphics.VertexArray;
import com.lyspal.flappy.input.Input;
import com.lyspal.flappy.math.Matrix4f;
import com.lyspal.flappy.math.Vector3f;

/**
 * A bird character.
 * 
 * @author sylvainlaporte
 */
public class Bird {

	private final float SIZE = 1.0f;
	private VertexArray mesh;
	private Texture texture;
	
	private Vector3f position = new Vector3f();
	private float rot;
	private float delta = 0.0f;
	
	/**
	 * Constructor.
	 */
	public Bird() {
		// Create vertices.
		float[] vertices = new float[] {
			//    X     ,       Y     ,   Z
			-SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
			-SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
			 SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
			 SIZE / 2.0f, -SIZE / 2.0f, 0.2f
		};
		
		// Create triangles with indices.
		byte[] indices = new byte[] {
				0, 1, 2,	// triangle 1
				2, 3, 0		// triangle 2
		};
		
		// Create texture coordinate system.
		float[] tcs = new float[] {
			0, 1,
			0, 0,
			1, 0,
			1, 1
		};
		
		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/bird.png");
	}
	
	/**
	 * Updates the bird position.
	 */
	public void update() {
		// Static fall when losing the game.
		position.y -= delta;
		// Move when space is down.
		if (Input.isKeyDown(GLFW_KEY_SPACE)) {
			delta = -0.15f;
		} else {
			delta += 0.01f;
		}
		// Rotate the bird.
		rot = -delta * 90.0f;
	}
	
	/**
	 * Makes the bird fall.
	 */
	public void fall() {
		delta = -0.15f;
	}
	
	/**
	 * Renders the bird.
	 */
	public void render() {
		Shader.BIRD.enable();
		Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.BIRD.disable();
	}

	/**
	 * Getter.
	 * 
	 * @return	the y position
	 */
	public float getY() {
		return position.y;
	}

	/**
	 * Getter.
	 * 
	 * @return	the size of the bird
	 */
	public float getSize() {
		return SIZE;
	}
}
