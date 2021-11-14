package ui;

import main.UIManager;
import utils.ANSICodes;

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
		System.out.println(ANSICodes.CLEAR_SCREEN + ANSICodes.CURSOR_TO_HOME);
		this.menuState = MenuState.CLOSE;
	}
	
	public String getResult() {
		return this.result;
	}
	
}
