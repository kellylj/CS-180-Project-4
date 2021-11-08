package ui;

import java.util.Scanner;

/**
 * 
 * Extension of OptionMenu that includes storing a result field.
 * Callback methods can mutate the result field such that
 * anything with a reference to the menu can determine the chosen option.
 * <p>
 * Called OptionMenuWithResult due to the presense of a result
 * coming from option chosen in the menu.
 * 
 * @author Isaac Fleetwood
 *
 * @param <T> Parameter for what type {@link #result} should be.
 */
public class OptionMenuWithResult<T> extends OptionMenu {

	public OptionMenuWithResult(Scanner scanner) {
		super(scanner);
	}

	T result;
	
	public void setResult(T result) {
		this.result = result;
	}
	
	public T getResult() {
		return result;
	}
	
}
