package com.lyspal.flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;

import com.lyspal.flappy.graphics.Shader;
import com.lyspal.flappy.input.Input;
import com.lyspal.flappy.level.Level;
import com.lyspal.flappy.math.Matrix4f;

/**
 * Entry point of the program.
 * <p>
 * Runnable = class that implements a runnable method.
 * 
 * @author sylvainlaporte
 */
public class Main implements Runnable {
	
	// Window dimensions
	private int width = 1280;
	private int height = 720;
	
	private boolean running = true;
	
	private long window;
	private Level level;

	/*
	 * =============== ABOUT IMPLEMENTATION FOR DIFFERENT OSes ===============
	 * 
	 * There are 3 concepts you need to be aware of:
	 * 		(A) window/context creation
	 * 		(B) running the event loop that dispatches events for the window
	 * 	and (C) making the context current in a thread.
	 * 
	 * - On Windows, (A) and (B) must happen on the same thread. It doesn't have
	 * 	 to be the main thread. (C) can happen on any thread.
	 * - On Linux, you can have (A), (B) and (C) on any thread.
	 * - On Mac OS X, (A) and (B) must happen on the same thread, that must also
	 *	 be the main thread (thread 0). Again, (C) can happen on any thread.
	 *
	 * A cross-platform application must be written such that it respects the
	 * strictest of the above scenarios (which is Mac OS X). Anything else means
	 * your application will break when it runs on different OSes.
	 * 
	 * For MacOS, you also need to run the JVM with -XstartOnFirstThread, because
	 * the main thread also has to be the first thread in the process (thread 0).
	 * 
	 * Source: http://forum.lwjgl.org/index.php?topic=5836.0
	 */
	
	
	// Create a new thread for the game
	
//	private Thread thread;
	
//	/**
//	 * Starts the game on a new thread.
//	 */
//	public void start() {
//		running = true;
//		thread = new Thread(this, "Game");
//		thread.start();
//	}
	
	
	/**
	 * Initialize the game.
	 * <p>
	 * Initialize everything that is required in update() and render(), including:
	 * <ol>
	 * 	<li>creating and showing a window;</li>
	 * 	<li>callbacks for controls;</li>
	 * 	<li>an OpenGL context;</li>
	 *  <li>enable depth test.</li>
	 * </ol>
	 */
	private void init() {
		// Initialize GLFW.
		
		if ( !glfwInit() ) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		// Create a window.
		
		window = glfwCreateWindow(width, height, "Flappy", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2,
								 (vidmode.height() - height) / 2);
		
		// Set the key callback for the controls.
		glfwSetKeyCallback(window, new Input());
		
		// Create an OpenGL context and show the window.
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		createCapabilities();
		
		// Enable depth test.
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		
		// Enable blend for fading.
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		// Print OpenGL version to console.
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		
		// Set the shaders.
		
		Shader.loadAll();
		
		// Create a projection matrix.
		Matrix4f pr_matrix = Matrix4f.orthographic(
			-10.0f, 10.0f,									// left, right
			-10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f,	// bottom, top
			-1.0f, 1.0f										// near, far
		);
		
		// Set parameters for the background shader.
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex", 1);
		
		// Set parameters for the bird shader.
		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniform1i("tex", 1);
		
		// Set parameters for the pipe shader.
		Shader.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.PIPE.setUniform1i("tex", 1);
		
		// Create a new level.
		level = new Level();
	} 
	
	/**
	 * Run the game.
	 * <p>
	 * On MacOs, init() OpenGl and render() have to be on the same thread.
	 */
	public void run() {
		init();
		
		// Create time variables to track ups and fps.
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		// Main game loop.
		while (running) {
			// Create a timer to keep the game running at 60 fps.
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				// Update the level.
				update();
				updates++;
				delta--;
			}
			// Render the next frame.
			render();
			frames++;
			
			// Display ups and fps in the console.
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			
			// Stop the game if the window is closed.
			if (glfwWindowShouldClose(window))
				running = false;
		}
		
		// Terminate GLFW when the game stops.
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	/**
	 * Game logic.
	 */
	private void update() {
		// Retreive control events.
		glfwPollEvents();
		// Update the level.
		level.update();
		// Restart the game if game over.
		if (level.isGameOver()) {
			level = new Level();
		}
	}
	
	/**
	 * Render the frames.
	 */
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();		
		glfwSwapBuffers(window);
	}
	
	/**
	 * Entry point of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
}
