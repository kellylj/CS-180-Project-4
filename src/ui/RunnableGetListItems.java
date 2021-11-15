package ui;

import java.util.List;

/**
 * Lambda for requesting the items in a list.
 * <p>
 * Runs whenever the menu opens, so that the most recent,
 * up-to-date list can be used.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @param <T>
 * TODO T extends Listable
 */
public interface RunnableGetListItems<T> {
	public List<T> getListItems();
}
