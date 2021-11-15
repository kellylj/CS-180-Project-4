package ui;

import main.UIManager;

/**
 * Extension of {@link MenuInput} that requires the input to be an integer.
 * <p>
 * If it is not, it will ask the user to enter a valid one and prompt them again.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see MenuInput
 */
public class MenuInputInt extends MenuInput {

	public MenuInputInt(String question, String resultKey) {
		super(question, resultKey);
	}

	@Override
	public String getInput(UIManager uiManager) {
		String input;
		while (true) {
			input = uiManager.getScanner().nextLine();
			if (input != null && isInteger(input))
				break;
			System.out.println("Please try again and enter a valid (integer) input.");
		}
		return input;
	}
	
	public boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
