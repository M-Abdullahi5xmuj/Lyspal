// Vertex shader for the pipe.
#version 330 core

layout (location = 0) in vec4 position;		// mesh position
layout (location = 1) in vec2 tc;			// texture coordinates

uniform mat4 pr_matrix;					// projection matrix
uniform mat4 vw_matrix = mat4(1.0);		// view matrix
uniform mat4 ml_matrix = mat4(1.0);		// model matrix

// Data going to the fragment shader.
out DATA
{
	vec2 tc;
	vec3 position;
} vs_out;

void main()
{
	// Set up the position of the pipe.
	gl_Position = pr_matrix * vw_matrix * ml_matrix * position;
	vs_out.tc = tc;

	// Light the pipes.
	vs_out.position = vec3(vw_matrix * ml_matrix * position);
}
