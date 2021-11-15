package main;

/**
 * An interface used for items that wish to be able 
 * to be shown in a {@link OptinListMenu}. All listable classes
 * must implement this interface, so that a friendly name
 * can be defined that can be shown to the user safely.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see OptionListMenu
 */
public interface Listable {

	public String getListName();
	
}
