package ui;

import main.Quiz;
import utils.Listable;

/**
 * Lambda used for when a user has selected
 * an item in {@link OptionListMenu}.
 * 
 * @author Isaac Fleetwood
 * @param T the type of the selected item
 * @see OptionListMenu
 */
public interface RunnableSelectListItem<T extends Listable> {
	public MenuState selectItem(T listItem);
}