package com.lyspal.flappy.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for files.
 * 
 * @author sylvainlaporte
 */
public class FileUtils {

	/**
	 * Constructor.
	 */
	private FileUtils() {
		
	}
	
	/**
	 * Reads a file and loads it as a string.
	 * 
	 * @param file		file to load
	 * @return			resulting string
	 */
	public static String loadAsString(String file) {
		// Use StringBuilder instead of String because it doesn't recalculate at
		// every append. Great for performances. Use it only in loops, though.
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer + "\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
}
