// Fragment shader for the bird.
#version 330 core

layout (location = 0) out vec4 color;	// color

// Data received from the vertex shader
in DATA
{
	vec2 tc;
} fs_in;

uniform sampler2D tex;		// texture data

void main()
{
	// Set the color of the bird.
	color = texture(tex, fs_in.tc);
	// Discard fragment if alpha is < 1.
	if (color.w < 1.0)
		discard;
}
