#version 330 core

layout (location = 0) out vec4 color;

// Data received from the vertex shader
in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;

uniform vec2 bird;
uniform sampler2D tex;
uniform int top;

void main()
{
	// Fix could not find uniform variable 'top'
	vec2 myTc = vec2(fs_in.tc.x, fs_in.tc.y);

	// Flip the top pipes
	if (top == 1)
		myTc.y = 1.0 - myTc.y;

	// Set up the color of the pipe
	color = texture(tex, myTc);

	// Discard fragment if alpha is < 1
	if (color.w < 1.0)
		discard;

	// Add lighthing
	color *= 2.0 / (length(bird - fs_in.position.xy) + 1.5) + 0.6;

	// Fix transparency
	color.w = 1.0;
}
