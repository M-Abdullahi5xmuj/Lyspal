#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tc;

uniform mat4 pr_matrix;

void main()
{
	// Set up the position of the backgroune.
	gl_Position = pr_matrix * position;
}
