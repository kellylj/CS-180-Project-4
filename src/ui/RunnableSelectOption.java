package ui;

/**
 * Lambda used for an associated
 * {@link MenuOption} has been selected.
 * 
 * 
 * @author Isaac Fleetwood
 * @see MenuOption
 * @see OptionMenu
 */
public interface RunnableSelectEvent {
	public MenuState onSelect();
}