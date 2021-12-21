#version 400 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCords;
layout (location = 2) in vec4 colors;

out vec2 pass_TextureCords;
out vec4 pass_Colors;

void main(void) {
    gl_Position = vec4(position, 1.0);
    pass_TextureCords = textureCords;
    pass_Colors = colors;
}