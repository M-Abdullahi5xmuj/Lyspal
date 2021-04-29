// Vertex shader for the background.
// Out of the vertex shader goes into the fragment shader.
// In from the fragment shader goes into the vertex shader.
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tc;

uniform mat4 pr_matrix;

out DATA
{
	vec2 tc;
} vs_out;	// vertex shader out

void main()
{
	// Set up the position of the backgroune.
	gl_Position = pr_matrix * position;

	vs_out.tc = tc;
}
