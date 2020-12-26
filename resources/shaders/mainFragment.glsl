#version 330 core

in vec4 ourColor;

uniform sampler2D Texture0;
varying vec2 vTexCoord;

void main()
{
    //gl_FragColor = texture2D(Texture0, vTexCoord);
    gl_FragColor = ourColor;
}