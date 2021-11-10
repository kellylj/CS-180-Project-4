package ui;

import java.util.Scanner;

import utils.ANSICodes;

public class MenuQuickInput extends Menu {
	
	Scanner scanner;
	String question;
	String result;
	
	public MenuQuickInput(Scanner scanner, String question) {
		this.scanner = scanner;
		this.question = question;
	}

	@Override
	public void runMenu() {
		System.out.println(ANSICodes.CYAN + ANSICodes.BOLD + question);
		this.result = scanner.nextLine();
		this.menuState = MenuState.CLOSE;
	}
	
	public String getResult() {
		return this.result;
	}
	
}
