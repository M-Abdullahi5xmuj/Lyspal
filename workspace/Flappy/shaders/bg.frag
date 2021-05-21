// Fragment shader for the background.
#version 330 core

layout (location = 0) out vec4 color;	// color

// Data received from the vertex shader
in DATA
{
	vec2 tc;			// texture coordinates
	vec3 position;		// mesh position
} fs_in;				// fragment shader in

uniform vec2 bird;			// bird position
uniform sampler2D tex;		// texture data

void main()
{
	// Set up the color of the background.
	color = texture(tex, fs_in.tc);

	// Add lighting around the bird.
	color *= 2.0 / (length(bird - fs_in.position.xy) + 2.5) + 0.7;
}
