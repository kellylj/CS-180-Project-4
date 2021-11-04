package ui;

import java.util.Scanner;

import utils.NumberUtils;

public class MenuInputInt extends MenuInput {

	public MenuInputInt(String question, String resultKey) {
		super(question, resultKey);
	}

	@Override
	public String getInput(Scanner scanner) {
		String input;
		while(true) {
			input = scanner.nextLine();
			if(input != null && NumberUtils.isInteger(input))
				break;
			System.out.println("Please try again and enter a valid (integer) input.");
		}
		return input;
	}
	
}
