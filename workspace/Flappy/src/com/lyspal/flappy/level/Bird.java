package com.lyspal.flappy.level;

import static org.lwjgl.glfw.GLFW.*;

import com.lyspal.flappy.graphics.Shader;
import com.lyspal.flappy.graphics.Texture;
import com.lyspal.flappy.graphics.VertexArray;
import com.lyspal.flappy.input.Input;
import com.lyspal.flappy.math.Matrix4f;
import com.lyspal.flappy.math.Vector3f;

public class Bird {

	private float SIZE = 1.0f;
	private VertexArray mesh;
	private Texture texture;
	
	private Vector3f position = new Vector3f();
	private float rot;
	private float delta = 0.0f;
	
	public Bird() {
		float[] vertices = new float[] {
			//    X     ,       Y     ,   Z
			-SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
			-SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
			 SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
			 SIZE / 2.0f, -SIZE / 2.0f, 0.2f
		};
		
		// Create triangle with indices.
		
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
		texture = new Texture("res/bird.png");
	}
	
	public void update() {
		// Static fall when losing the game.
		position.y -= delta;
		if (Input.isKeyDown(GLFW_KEY_SPACE)) {
			delta = -0.15f;
		} else {
			delta += 0.01f;
		}
		
		// Rotate the bird.
		rot = -delta * 90.0f;
	}
	
//	private void fall() {
//		delta = -0.15f;
//	}
	
	public void render() {
		Shader.BIRD.enable();
		Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.BIRD.disable();
	}
}
