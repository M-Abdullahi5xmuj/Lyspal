package com.lyspal.flappy.math;

import java.nio.FloatBuffer;

import com.lyspal.flappy.utils.BufferUtils;

public class Matrix4f {

	public static final int SIZE = 4 * 4;
	public float[] elements = new float[SIZE];
	
	/**
	 * Constructor.
	 */
	public Matrix4f() {
	}
	
	/**
	 * Create an identity matrix of size 4 * 4.
	 * 
	 * @return	an identity matrix
	 */
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();
		
		// Unecessary because is the default behavior, but useful to show in code.
		
		// Fill the matrix with 0s.
		
		for (int i = 0; i < SIZE; i++) {
			result.elements[i] = 0.0f;
		}
		
		// Place the 1 elements on the diagonal.
		//			   row col
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;
		
		return result;
	}
	
	/**
	 * Creates an orthographic 4 * 4 matrix for orthographic projection.
	 * Orthographic projection: objects farther in the z direction remain of the same size.
	 * 
	 * @param left
	 * @param right
	 * @param bottom	six clipping planes
	 * @param top
	 * @param near
	 * @param far
	 * @return			an orthographic 4 * 4 matrix
	 */
	public static Matrix4f orthographic(float left, float right,
										float bottom, float top,
										float near, float far) {
		
		Matrix4f result = identity();
		
		// Fill the matrix.
		
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		
		result.elements[2 + 2 * 4] = 2.0f / (near - far);
		
		result.elements[0 + 3 * 4] = (left + right) / (left - right);
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.elements[2 + 3 * 4] = (far + near) / (far - near);
		
		return result;
	}
	
	/**
	 * Creates a 4 * 4 translation matrix.
	 * 
	 * @param vector	a Vector3f representing the direction of the translation
	 * @return			a translation matrix
	 */
	public static Matrix4f translate(Vector3f vector) {
		
		Matrix4f result = identity();
		
		// Fill the matrix.
		
		result.elements[0 + 3 * 4] = vector.x;
		result.elements[1 + 3 * 4] = vector.y;
		result.elements[2 + 3 * 4] = vector.z;
		
		return result;
	}
	
	/**
	 * Create a 4 * 4 rotation matrix.
	 * 
	 * @param angle		the rotation angle
	 * @return			a rotation matrix
	 */
	public static Matrix4f rotate(float angle) {
		
		Matrix4f result = identity();
		
		float r = (float) Math.toRadians(angle);
		
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		// Fill the matrix.
		
		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;
		
		return result;
	}
	
	/**
	 * Implements matrix multiplication.
	 * 
	 * @param matrix	a matrix B to be multiplied by this
	 * @return			the AB matrix
	 */
	public Matrix4f multiply(Matrix4f matrix) {
		
		Matrix4f result = new Matrix4f();
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		
		return result;
	}
	
	/**
	 * A way to go from FloatArray to FloatBuffer.
	 */
	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(elements);
	}
}
