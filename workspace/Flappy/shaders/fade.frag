// Fragment shader for the fade.
#version 330 core

layout (location = 0) out vec4 color;	// color

uniform float time;		// time

void main()
{
	// Reduce opacity with time.
	if (time > 1.0)
		discard;
	color = vec4(1.0, 1.0, 1.0, 1.0 - time);
}
