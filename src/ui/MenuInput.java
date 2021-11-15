package ui;

import java.util.Scanner;

import main.UIManager;
import utils.ANSICodes;

/**
 * Datastructure used by {@link InputMenu} to store information about the input the menu should ask for.
 * <p>
 * Contains a question and the key for where to store the resulting data in the HashMap in InputMenu ({@link InputMenu#values}).
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see InputMenu
 */
public class MenuInput {
	String question;
	String resultKey;

	public MenuInput(String question, String resultKey) {
		this.question = question;
		this.resultKey = resultKey;
	}

	public String getQuestion() {
		return this.question;
	}

	public String getResultKey() {
		return this.resultKey;
	}
	
	public String getInput(UIManager uiManager) {
		String input;
		while(true) {
			input = uiManager.getScanner().nextLine();
			if(input != null && input.length() > 0)
				break;
			System.out.println("Please try again and enter a valid input.");
		}
		return input;
	}
	
}