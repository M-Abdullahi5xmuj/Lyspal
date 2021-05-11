#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;	// fragment shader in

//uniform vec2 bird;
uniform sampler2D tex;
//uniform int top;

void main()
{
//	if (top == 1)
//		fs_in.tc.y = 1.0 - fs_in.tc.y;		// Bug: Left-hand-side of assignment must not be read-only

	// Set up the color of the pipe.
	color = texture(tex, fs_in.tc);
	// Discard fragment if alpha is < 1.
	if (color.w < 1.0)
		discard;
//	color *= 2.0 / (length(bird - fs_in.position.xy) + 1.5) + 0.5;
	color.w = 1.0;
}
