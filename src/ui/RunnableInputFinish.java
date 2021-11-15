package ui;

import java.util.Map;

/**
 * Lambda used for when {@link InputMenu} has 
 * finished getting all of it's inputs.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see InputMenu
 */
public interface RunnableInputFinish {
	public MenuState onInputFinish(Map<String, String> results);
}