package ui;

/**
 * 
 * Datastructure used by {@link OptionMenu} representing an option that can be chosen in a menu.
 * Contains a {@link String} for the name of the option, and a {@link RunnableSelectOption} for a callback function 
 * that runs whenever the option is selected.
 * <p>
 * The callback function will determine if the menu will close or stay open and restart via its result of {@link MenuState}
 * <p>
 * The callback can be added via method chaining, like seen in {@link OptionMenu}
 * 
 * @author Isaac Fleetwood
 * 
 * @see OptionMenu
 * @see MenuOptionConditional
 */
public class MenuOption {

	private RunnableSelectOption onSelectRunnable;
	private RunnableCheckCondition visibilityCondition;

	private String option;

	/**
	 * Creates a {@link MenuOption} with a given string that will be shown to the user that is representative of the option.
	 * 
	 * @param option - The string shown to the user in the menu.
	 */
	public MenuOption(String option) {
		this.option = option;
	}

	/**
	 * Returns the string used to represent the option.
	 * 
	 * @return option - The string shown to the user in the menu.
	 */
	public String getOptionString() {
		return this.option;
	}

	/**
	 * Sets the callback for whenever the option is selected.
	 * 
	 * @param onSelectRunnable - Callback function to be ran when the option is selected.
	 * @return itself - Used for method chaining
	 */
	public MenuOption onSelect(RunnableSelectOption onSelectRunnable) {
		this.onSelectRunnable = onSelectRunnable;
		return this;
	}
	
	public MenuState onSelect() {
		if (this.onSelectRunnable != null)
			return this.onSelectRunnable.onSelect();
		return MenuState.CLOSE;
	}
	
	public MenuOption addVisibilityCondition(RunnableCheckCondition visibilityCondition) {
		this.visibilityCondition = visibilityCondition;
		return this;
	}
	
	/**
	 * Function used for determining if the option is visible.
	 * <p>
	 * If {@link #addVisibilityCondition(RunnableCheckCondition)} was not ran during object creation, 
	 * it will always return true. Otherwise, if it was ran, it will determine visiblity based on the
	 * result of the visibility condition.
	 * 
	 * @return Boolean for whether the option is visible or not.
	 */
	public boolean isVisible() {
		if(this.visibilityCondition == null)
			return true;
		return this.visibilityCondition.check();
	}

}