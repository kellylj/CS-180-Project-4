package ui;

import java.util.Map;

/**
 * Lambda used for when {@link InputMenu} has 
 * finished getting all of it's inputs.
 * 
 * @author Isaac Fleetwood
 * @see InputMenu
 */
public interface RunnableInputFinishEvent {
	public MenuState onInputFinish(Map<String, String> results);
}