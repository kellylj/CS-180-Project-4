package ui;

import main.UIManager;

/**
 * Datastructure used by {@link InputMenu} for menu inputs where an option is desired.
 * <p>
 * Internally uses its own {@link OptionMenuWithResult} to store a list of {@link String}
 * options and prompt the user to choose one.
 * <p>
 * Once the user chooses a valid option, the resulting chosen string is returned.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see InputMenu
 */
public class MenuInputOptions extends MenuInput {

	OptionMenuWithResult<String> optionMenu;
	
	public MenuInputOptions(String question, String[] options, String resultKey, UIManager uiManager) {
		super(question, resultKey);
		this.optionMenu = new OptionMenuWithResult<String>(uiManager);
		for (String option: options) {
			optionMenu.addOption(((new MenuOption(option))
				   .onSelect(() -> {
					   optionMenu.setResult(option);
					   return MenuState.CLOSE;
			   	   })
			));
		}
	}
	
	/**
	 * Method ran when wanting to prompt the user for input.
	 * <p>
	 * Opens the {@link #optionMenu} menu where it will ask the user to choose an option.
	 * Once it exits, 
	 * 
	 * @return The chosen option in the form of a {@link String}
	 */
	@Override
	public String getInput(UIManager uiManager) {
		this.optionMenu.open();
		return this.optionMenu.getResult();
	}
	
}