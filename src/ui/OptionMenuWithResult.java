package ui;

import main.UIManager;

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
 * @version 1.0.0
 *
 * @param <T> Parameter for what type {@link #result} should be.
 */
public class OptionMenuWithResult<T> extends OptionMenu {

	public OptionMenuWithResult(UIManager uiManager) {
		super(uiManager);
	}

	T result;
	
	public void setResult(T result) {
		this.result = result;
	}
	
	public T getResult() {
		return result;
	}
	
}
