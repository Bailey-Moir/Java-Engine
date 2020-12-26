#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoord;

out vec4 ourColor;

varying vec2 vTexCoord;

void main(void)
{
    vTexCoord = aTexCoord;

    gl_Position = vec4(aPos, 1.0);
    ourColor = aColor;
}