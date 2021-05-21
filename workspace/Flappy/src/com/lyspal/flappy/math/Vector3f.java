package com.lyspal.flappy.math;

/**
 * A 3D float vector.
 * 
 * @author sylvainlaporte
 */
public class Vector3f {

	public float x, y, z;	// z is for render order.
	
	/**
	 * Constructor.
	 */
	public Vector3f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y		the three components of the vector.
	 * @param z
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
