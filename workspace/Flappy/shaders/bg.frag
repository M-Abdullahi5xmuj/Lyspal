#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;	// received from the vertex shader

uniform vec2 bird;
uniform sampler2D tex;

void main()
{
	// Set up the color of the background.
	color = texture(tex, fs_in.tc);
	// Add lighting
	color *= 3.0 / (length(bird - fs_in.position.xy) + 2.5) + 0.3;
}
