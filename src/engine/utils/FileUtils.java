package engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Utilities to navigate the file system.
 * 
 * @author Bailey
 */

@SuppressWarnings("unused")
public class FileUtils {
	/**
	 * Loads a file as a string into a string, like the 'cat' command.
	 * @param path The path of the file.
	 * @return The text in the file as a string.
	 */
	public static String loadAsString(String path) {
		StringBuilder result = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						Objects.requireNonNull(
								Class.class.getResourceAsStream(path)
						)
				)
			)
		) { // Makes a reader for the resources, from inputted file.
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line).append("\n");
			}
		} catch (IOException e) {
			System.err.println("Couldn't find the file at " + path);
		}
		return result.toString();
	}
}
