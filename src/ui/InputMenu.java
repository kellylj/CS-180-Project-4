package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.UIManager;
import utils.ANSICodes;

/**
 * Class extending {@link Menu} that adds the option of prompting the 
 * user for input for object creation.
 * <p>
 * The menu can be created through the use of a builder-like pattern. 
 * <p>
 * Inputs and any callbacks are passed to the menu before the UI runs the menu. 
 * Each relevant input will be stored in a HashMap, which will then get passed 
 * to the given callback function ({@link #inputFinishEventRunnable}) for 
 * handling the resultant data
 * <p>
 * {@link #addValidationRequest()} can be used to prompt the user 
 * to verify the data is correct.
 * 
 * @author Isaac Fleetwood
 *
 * @see UIManager
 */
public class InputMenu extends Menu {

	UIManager uiManager;
	
	ArrayList<String> headings;
	ArrayList<MenuInput> inputs;
	Map<String, String> values;
	RunnableInputFinishEvent inputFinishEventRunnable;
	boolean validationRequest;

	public InputMenu(UIManager uiManager) {
		this.uiManager = uiManager;
		this.inputs = new ArrayList<MenuInput>();
		this.headings = new ArrayList<String>();
		this.values = new HashMap<String, String>();
	}

	public InputMenu addHeading(String heading) {
		headings.add("\n" + ANSICodes.BOLD + heading + ANSICodes.RESET);
		return this;
	}

	public InputMenu addSubheading(String subheading) {
		headings.add(subheading);
		return this;
	}

	public InputMenu addInput(String question, String keyResult) {
		this.inputs.add(new MenuInput(question, keyResult));
		return this;
	}

	public InputMenu addIntInput(String question, String keyResult) {
		this.inputs.add(new MenuInputInt(question, keyResult));
		return this;
	}

	public InputMenu addInputWithOptions(String question, String[] options, String keyResult) {
		this.inputs.add(new MenuInputOptions(question, options, keyResult, uiManager));
		return this;
	}

	public InputMenu onInputFinish(RunnableInputFinishEvent inputFinishEventRunnable) {
		this.inputFinishEventRunnable = inputFinishEventRunnable;
		return this;
	}

	/**
	 * 
	 * Sets {@link #validationRequest} to true.
	 * When the menu is run, the user will be prompted to verify that the data
	 * they entered is correct. If they say it isn't they will be given the
	 * option to try again or exit the menu without creating anything.
	 * 
	 * @return
	 */
	public InputMenu addValidationRequest() {
		this.validationRequest = true;
		return this;
	}

	private enum ValidationState {
		CONTINUE, RESTART, EXIT;
	}
	
	private ValidationState requestValidation() {
		OptionMenuYesNo verifyMenu = new OptionMenuYesNo(uiManager);
		verifyMenu.addHeading("The following are the values you gave:");
		for (String key : values.keySet()) {
			verifyMenu.addSubheading(key + " - " + values.get(key));
		}
		verifyMenu.addHeading("Are you satisfied with these values?");
		verifyMenu.open();
		boolean isYes = verifyMenu.getResult();
		if (isYes)
			return ValidationState.CONTINUE;

		OptionMenuYesNo tryAgainMenu = new OptionMenuYesNo(uiManager);
		tryAgainMenu.addHeading("Do you want to try again?");
		tryAgainMenu.open();
		boolean boolTryAgain = tryAgainMenu.getResult();
		if (boolTryAgain) {
			System.out.println("Okay.");
			return ValidationState.RESTART;
		}

		System.out.println("Okay. Going back to the menu.");
		return ValidationState.EXIT;
	}

	/**
	 * Runs the menu. Prints out all headings
	 * then prompts the user for input based on the inputs given beforehand.
	 * <p>
	 * If {@link #addValidationRequest()} was ran beforehand, it will call {@link #requestValidation()} to make the user verify the data is correct.
	 * <p>
	 * If {@link #addValidationRequest()} was never ran, or {@link #requestValidation()} returns {@link ValidationState.CONTINUE} then 
	 * {@link #inputFinishEventRunnable} will be ran with the HashMap containing the data from the input.
	 * <p>
	 * @param None - However, it will use the previous data given via methods like {@link #addInput(String, String)} {@link #addHeading(String)} and {@link #addValidationRequest()}.
	 * If you are having problems, verify those methods are giving the correct data.
	 */
	public void runMenu() {
		for (String heading : headings) {
			System.out.println(heading);
		}
		
		this.values.clear();

		for (int i = 0; i < inputs.size(); i++) {
			MenuInput input = inputs.get(i);

			System.out.println(ANSICodes.BOLD + ANSICodes.CYAN + input.getQuestion() + ANSICodes.RESET);

			String result = input.getInput(uiManager.getScanner());

			values.put(input.getResultKey(), result);
		}

		if (validationRequest) {
			ValidationState validationState = this.requestValidation();
			if (validationState.equals(ValidationState.EXIT)) {
				menuState = MenuState.CLOSE;
				return;
			}
			if (validationState.equals(ValidationState.RESTART)) {
				menuState = MenuState.RESTART;
				return;
			}
		}

		menuState = this.inputFinishEventRunnable.onInputFinish(values);
	}

}