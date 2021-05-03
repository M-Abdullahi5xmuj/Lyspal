#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
} fs_in;	// fragment shader in

uniform sampler2D tex;

void main()
{
	// Set up the color of the backgroune.
	color = texture(tex, fs_in.tc);
}
