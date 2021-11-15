package ui;

/**
 * The running state of the menu in which it is used.
 * <p>
 * Is used in {@link Menu} to determine if the menu will restart 
 * once it exits, or if it will close.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see Menu
 */
public enum MenuState {
	RESTART,
	CLOSE;
}