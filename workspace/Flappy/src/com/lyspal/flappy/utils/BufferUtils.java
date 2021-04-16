package com.lyspal.flappy.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

	private BufferUtils() {
	}
	
	/**
	 * Create a byte buffer.
	 * 
	 * @param array		an array to put in buffer
	 * @return			the buffered array
	 */
	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer result = ByteBuffer.allocateDirect(array.length)
				.order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}
	
	/**
	 * Create a float buffer.
	 * 
	 * @param array		an array to put in buffer
	 * @return			the buffered array
	 */
	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2)
									// << 2 = bit shift by 2 to create more space.
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}
	
	/**
	 * Create an int buffer.
	 * 
	 * @param array		an array to put in buffer
	 * @return			the buffered array
	 */
	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2)
									// << 2 = bit shift by 2 to create more space.
				.order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}
}
