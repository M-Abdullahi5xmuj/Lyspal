// Vertex shader for the background.
#version 330 core

layout (location = 0) in vec4 position;		// mesh position
layout (location = 1) in vec2 tc;			// texture coordinates

uniform mat4 pr_matrix;		// projection matrix
uniform mat4 vw_matrix;		// view matrix, camera position

// Data going to the fragment shader.
out DATA
{
	vec2 tc;
	vec3 position;
} vs_out;				// vertex shader out

void main()
{
	// Set up the position of the background.
	gl_Position = pr_matrix * vw_matrix * position;
	vs_out.tc = tc;

	// Light around the bird.
	vs_out.position = vec3(vw_matrix * position);
}
