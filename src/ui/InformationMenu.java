package ui;

import java.util.StringJoiner;

import main.UIManager;
import utils.ANSICodes;

public class InformationMenu extends Menu {

	UIManager uiManager;
	StringJoiner text;
	int listIndex;
	boolean requiresEnter;

	public InformationMenu(UIManager uiManager) {
		this.uiManager = uiManager;
		this.text = new StringJoiner("\n");
		this.requiresEnter = false;
		this.listIndex = 1;
	}
	
	public InformationMenu addHeading(String heading) {
		text.add("\n" + ANSICodes.BOLD + heading + ANSICodes.RESET);
		return this;
	}
	
	public InformationMenu addFakeInputText(String normalText) {
		text.add(ANSICodes.CYAN + ANSICodes.BOLD + normalText + ANSICodes.RESET);
		return this;
	}
	
	public InformationMenu addText(String normalText) {
		text.add(normalText);
		return this;
	}
	
	public InformationMenu addListItem(String item) {
		text.add(ANSICodes.CYAN + ANSICodes.BOLD + listIndex + ": " + ANSICodes.RESET + item);
		listIndex += 1;
		return this;
	}
	
	public InformationMenu requireEnter() {
		this.requiresEnter = true;
		return this;
	}
	
	@Override
	public void runMenu() {
		System.out.println(text.toString());
		if(this.requiresEnter) {
			System.out.println("Please press Enter to continue.");
			this.uiManager.getScanner().nextLine();
		}
		// TODO Fun
		System.out.println(ANSICodes.CLEAR_SCREEN + ANSICodes.CURSOR_TO_HOME);
		this.menuState = MenuState.CLOSE;
	}

	
	
}
