package engine.graphics.shaders;

import org.lwjgl.opengl.GL30;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * An abstract shader program.
 *
 * @author Bailey
 */

public abstract class ShaderProgram {	
	private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;
    
    /**
     * The constructor.
     * @param vertexFile The location of the vertex shader.
     * @param fragmentFile The location of the fragment shader.
     */
    public ShaderProgram(String vertexFile,String fragmentFile){
        vertexShaderID = loadShader(vertexFile,GL30.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL30.GL_FRAGMENT_SHADER);
        programID = GL30.glCreateProgram();
        GL30.glAttachShader(programID, vertexShaderID);
        GL30.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL30.glLinkProgram(programID);
        GL30.glValidateProgram(programID);
    }

    /**
     * Starts the shader program.
     */
    public void start(){
        GL30.glUseProgram(programID);
    }

    /**
     * Stops the shader program
     */
    public void stop(){
        GL30.glUseProgram(0);
    }

    /**
     * Deletes the shaders out of memory, for memory management.
     */
    public void cleanUp(){
        stop();
        GL30.glDetachShader(programID, vertexShaderID);
        GL30.glDetachShader(programID, fragmentShaderID);
        GL30.glDeleteShader(vertexShaderID);
        GL30.glDeleteShader(fragmentShaderID);
        GL30.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    /**
     * Binds an attribute
     * @param attribute The index of the attribute
     * @param variableName The name of the variable.
     */
    protected void bindAttribute(int attribute, String variableName){
        GL30.glBindAttribLocation(programID, attribute, variableName);
    }

    /**
     * Loads the shader from a file
     * @param file The location of the file.
     * @param type Whether it is fragment or vertex.
     * @return The shader id.
     */
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL30.glCreateShader(type);
        GL30.glShaderSource(shaderID, shaderSource);
        GL30.glCompileShader(shaderID);
        if(GL30.glGetShaderi(shaderID, GL30.GL_COMPILE_STATUS )== GL30.GL_FALSE){
            System.out.println(GL30.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

}