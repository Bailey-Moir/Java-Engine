#version 330 core

layout(location = 0) in vec3 position; //value 1 is full screen width/height
layout(location = 1) in vec3 color;

out vec3 passColor;

void main() {
	gl_Position = vec4(position, 1.0);
	//passColor = color; //The vector variables are the pixel colors.
	passColor = color;
}