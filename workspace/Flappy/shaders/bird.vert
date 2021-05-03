// Vertex shader for the bird.
// Out of the vertex shader goes into the fragment shader.
// In from the fragment shader goes into the vertex shader.
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tc;

uniform mat4 pr_matrix;					// Projection matrix.
uniform mat4 vw_matrix = mat4(1.0);		// View matrix.
uniform mat4 ml_matrix = mat4(1.0);		// Model matrix.

out DATA
{
	vec2 tc;
} vs_out;	// vertex shader out

void main()
{
	// Set up the position of the bird.
	gl_Position = pr_matrix * vw_matrix * ml_matrix * position;
	vs_out.tc = tc;
}
