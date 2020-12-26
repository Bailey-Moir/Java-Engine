#version 400 core

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main()
{
    vec4 textureColor = texture(textureSampler, pass_textureCoords);

    //Allows for transparent backgrounds
    if (textureColor.a < 0.5) {
        discard;
    }

    out_Color = textureColor;
}