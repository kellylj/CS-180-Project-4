package ui;

import java.util.Scanner;

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
		System.out.println(this.question);
		this.result = scanner.nextLine();
		this.menuState = MenuState.CLOSE;
	}
	
	public String getResult() {
		return this.result;
	}
	
}
