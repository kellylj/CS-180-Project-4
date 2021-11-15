package ui;

import main.UIManager;

/**
 * Menu for requesting a single of input from the user
 * quickly, without the need for a callback function.
 * Once opened, the result of the prompt will be set
 * and can be accessed via {@link MenuQuickInput#getResult()}
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see Menu
 * @see InputMenu
 */
public class MenuQuickInput extends Menu {
	
	UIManager uiManager;
	String question;
	String result;
	
	public MenuQuickInput(UIManager uiManager, String question) {
		this.uiManager = uiManager;
		this.question = question;
	}

	@Override
	public void runMenu() {
		System.out.println(ANSICodes.CYAN + ANSICodes.BOLD + question + ANSICodes.RESET);
		this.result = uiManager.getScanner().nextLine();
		// TODO Fun
		System.out.print(ANSICodes.CLEAR_SCREEN + ANSICodes.CURSOR_TO_HOME);
		this.menuState = MenuState.CLOSE;
	}
	
	public String getResult() {
		return this.result;
	}
	
}
