package com.lyspal.flappy.level;

import com.lyspal.flappy.graphics.Texture;
import com.lyspal.flappy.graphics.VertexArray;
import com.lyspal.flappy.math.Matrix4f;
import com.lyspal.flappy.math.Vector3f;

/**
 * A pipe asset.
 */
public class Pipe {

	private static float width = 1.5f, height = 8.0f;
	private static Texture texture;
	private static VertexArray mesh;
	
	/**
	 * Creates the pipe mesh and texture.
	 */
	public static void create() {
		// Create the vertices.
		float[] vertices = new float[] {
			0.0f, 0.0f, 0.1f,
			0.0f, height, 0.1f,
			width, height, 0.1f,
			width, 0.0f, 0.1f
		};
		
		// Create the triangles with indices.
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
		
		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/pipe.png");
	}
	
	private Vector3f position = new Vector3f();
	private Matrix4f ml_matrix;
	
	/**
	 * Constructor.
	 * 
	 * @param x		the x position
	 * @param y		the y position
	 */
	public Pipe(float x, float y) {
		position.x = x;
		position.y = y;
		ml_matrix = Matrix4f.translate(position);
	}
	
	// Getters.
	
	public float getX() {
		return position.x;
	}
	public float getY() {
		return position.y;
	}
	
	public Matrix4f getModelMatrix() {
		return ml_matrix;
	}
	
	public static VertexArray getMesh() {
		return mesh;
	}
	
	public static Texture getTexture() {
		return texture;
	}
	
	public static float getWidth() {
		return width;
	}
	
	public static float getHeight() {
		return height;
	}

}
