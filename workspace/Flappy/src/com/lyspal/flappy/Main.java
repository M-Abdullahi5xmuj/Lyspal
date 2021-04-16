package com.lyspal.flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;

import com.lyspal.flappy.input.Input;

public class Main implements Runnable {		// Runnable = class that has a runnable method.
	
	private int width = 1280;			// window width.
	private int height = 720;			// window height.
	
	private long window;				// window identifier.
	
	private boolean running = true;		// game running.


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
	 * Initialize everything that is required in update() and render(), including:
	 * 	- creating and showing a window;
	 * 	- callbacks for controls;
	 * 	- an OpenGL context;
	 *  - enable depth test.
	 */
	private void init() {
		if ( !glfwInit() ) {
			// TODO: handle it
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		// Create a window.
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		window = glfwCreateWindow(width, height, "Flappy", NULL, NULL);
		
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		// Position the window.
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		// Set the key callback for controls.
		
		glfwSetKeyCallback(window, new Input());
		
		// Show the window and create an OpenGL context.
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		createCapabilities();
		
		// Set clear color, enable depth test and print OpenGL version to console.
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
	} 
	
	/**
	 * Method run by the new thread "Game" for rendering and game logic.
	 */
	public void run() {
		init();				// init() OpenGl and render() have to be on the same thread.
		while (running) {
			update();
			render();
			
			if (glfwWindowShouldClose(window))
				running = false;
		}
	}
	
	/**
	 * Game logic.
	 */
	private void update() {
		glfwPollEvents();

	}
	
	/**
	 * Rendering.
	 */
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window);
	}
	
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
