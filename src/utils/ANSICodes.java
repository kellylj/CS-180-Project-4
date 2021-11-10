package utils;

import ui.InputMenu;
import ui.OptionMenu;

/**
 * 
 * Wrapper class containing all ANSI Codes used in the project. Contains codes for changing the color or background color of text.
 * <p>
 * This class is used by printing one of the codes in front of a string, causing the string to change color in the final terminal output.
 * <p>
 * NOTE: ANSICodes.{@link #RESET} must be used when the specified color has to stop. Otherwise that color will maintain until ANSICodes.{@link #RESET} is printed.
 * <p>
 * ANSI can be used for more things, including moving the cursor and clearing the screen. However there is no guarantee on widespread compatibility with this, so we decided to not use it.
 * 
 * @author Isaac Fleetwood
 * 
 * @see InputMenu#runMenu()
 * @see OptionMenu#runMenu()
 */
public class ANSICodes {

	public static final String	CLEAR_SCREEN		= "\u001B[2J";
	public static final String	CURSOR_TO_HOME		= "\u001B[0;0H";
	
	// Used from
	// https://gist.github.com/dainkaplan/4651352
	// With some modification
	public static final String	RESET				= "\u001B[0m";

	public static final String	BOLD				= "\u001B[1m";
	public static final String	LIGHT				= "\u001B[2m";

	public static final String	ITALIC				= "\u001B[3m";
	public static final String	UNDERLINE			= "\u001B[4m";
	public static final String	BLINK				= "\u001B[5m";
	public static final String	RAPID_BLINK			= "\u001B[6m";
	public static final String	REVERSE_VIDEO		= "\u001B[7m";
	public static final String	INVISIBLE_TEXT		= "\u001B[8m";

	public static final String	BLACK				= "\u001B[30m";
	public static final String	RED					= "\u001B[31m";
	public static final String	GREEN				= "\u001B[32m";
	public static final String	YELLOW				= "\u001B[33m";
	public static final String	BLUE				= "\u001B[34m";
	public static final String	MAGENTA				= "\u001B[35m";
	public static final String	CYAN				= "\u001B[36m";
	public static final String	WHITE				= "\u001B[37m";

	public static final String	BACKGROUND_BLACK	= "\u001B[40m";
	public static final String	BACKGROUND_RED		= "\u001B[41m";
	public static final String	BACKGROUND_GREEN	= "\u001B[42m";
	public static final String	BACKGROUND_YELLOW	= "\u001B[43m";
	public static final String	BACKGROUND_BLUE		= "\u001B[44m";
	public static final String	BACKGROUND_MAGENTA	= "\u001B[45m";
	public static final String	BACKGROUND_CYAN		= "\u001B[46m";
	public static final String	BACKGROUND_WHITE	= "\u001B[47m";
	
	public static String stripCodes(String s) {
		return s
			.replaceAll("\u001B\\[\\d+[a-zA-Z]", "")
			.replaceAll("\u001B\\[\\d+;\\d+[a-zA-Z]", "");
	}
	
}