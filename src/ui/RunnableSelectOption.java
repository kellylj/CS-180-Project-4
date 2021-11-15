package ui;

/**
 * Lambda used for an associated
 * {@link MenuOption} has been selected.
 * 
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see MenuOption
 * @see OptionMenu
 */
public interface RunnableSelectOption {
	public MenuState onSelect();
}