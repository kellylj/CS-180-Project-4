package ui;

/**
 * Lambda used for when a user has selected
 * an item in {@link OptionListMenu}.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @param <T> the type of the selected item
 * @see OptionListMenu
 */
public interface RunnableSelectListItem<T> {
	public MenuState selectItem(T listItem);
}