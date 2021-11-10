package ui;

import java.util.ArrayList;

/**
 * Lambda for requesting the items in a list.
 * <p>
 * Runs whenever the menu opens, so that the most recent,
 * up-to-date list can be used.
 * 
 * @author Isaac Fleetwood
 * @param <T>
 * TODO T extends Listable
 */
public interface RunnableGetListItems<T> {
	public ArrayList<T> getListItems();
}
