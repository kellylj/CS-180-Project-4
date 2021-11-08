package ui;

import utils.Listable;

/**
 * Lambda used for when a user has selected
 * an item in {@link OptionListMenu}.
 * 
 * @author Isaac Fleetwood
 * @param T the type of the selected item
 * @see OptionListMenu
 * TODO T extends Listable
 */
public interface RunnableSelectListItem<T> {
	public MenuState selectItem(T listItem);
}