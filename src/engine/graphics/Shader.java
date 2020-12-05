package engine.graphics;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

import engine.utils.FileUtils;

/**
 * Manages shaders
 * 
 * @author Bailey
 */

public class Shader {
	private String vertexFile, fragmentFile;
	private int vertexID, fragmentID, programID;
	
	/**
	 * Default constructor. Sets some member values.
	 * @param vertexPath The location of the vertex shader relative to resources.
	 * @param fragmentPath The location of the fragment shader relative to the resources.
	 */
	public Shader(String vertexPath, String fragmentPath) {
		vertexFile = FileUtils.loadAsString(vertexPath);
		fragmentFile = FileUtils.loadAsString(fragmentPath);
	}

	/**
	 * Initlizes/creates the shader.
	 */
	public void create() {
		programID = GL33.glCreateProgram();
		vertexID = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
		fragmentID = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);
		
		GL33.glShaderSource(vertexID, vertexFile);
		GL33.glShaderSource(fragmentID, fragmentFile);
		GL33.glCompileShader(vertexID);
		GL33.glCompileShader(fragmentID);
		
		//If it failed
		if (GL33.glGetShaderi(vertexID, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) {
			System.err.println("Vertex Shader: " + GL33.glGetShaderInfoLog(vertexID));
			return;
		}
		if (GL33.glGetShaderi(fragmentID, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) {
			System.err.println("Fragment Shader: " + GL33.glGetShaderInfoLog(fragmentID));
			return;
		}
		
		//Attachs shaders
		GL33.glAttachShader(programID, vertexID);
		GL33.glAttachShader(programID, fragmentID);
		
		GL33.glLinkProgram(programID);
		//If link fails...
		if (GL33.glGetProgrami(programID, GL33.GL_LINK_STATUS) == GL33.GL_FALSE) {
			System.err.println("Program Linking: " + GL33.glGetProgramInfoLog(programID));
			return;
		}
		
		GL33.glValidateProgram(programID);
		//If validation fails...
		if (GL33.glGetProgrami(programID, GL33.GL_VALIDATE_STATUS) == GL33.GL_FALSE) {
			System.err.println("Program Validation: " + GL33.glGetProgramInfoLog(programID));
			return;
		}
		
		//Deletes the shaders
		GL33.glDeleteShader(vertexID);
		GL33.glDeleteShader(fragmentID);
	}
	
	/**
	 * Binds the shader, i.e. uses it.
	 */
	public void bind() {
		GL20.glUseProgram(programID);
	}
	
	/**
	 * Unbinds the shader, i.e. stops using it.
	 */
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	/**
	 * Destroys the shader.
	 */
	public void destroy() {
		GL20.glDeleteProgram(programID);
	}
}
