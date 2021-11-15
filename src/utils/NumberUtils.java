package utils;

/**
 * 
 * Utility class for basic number functions 
 *    that aren't available by default in Java
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 *
 */
public class NumberUtils {

	public static boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
}