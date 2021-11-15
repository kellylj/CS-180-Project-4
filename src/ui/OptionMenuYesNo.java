package ui;

import main.UIManager;

/**
 * Option menu for simple yes/no choices.
 * <p>
 * {@link #getResult()} will return true for Yes and false for No.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 *
 */
public class OptionMenuYesNo extends OptionMenuWithResult<Boolean> {
	
	public OptionMenuYesNo(UIManager uiManager) {
		super(uiManager);
		
		this.options.clear();
		
		this.addOption((new MenuOption("Yes")).onSelect(() -> {
			this.setResult(true);
			return MenuState.CLOSE;
		}));
		
		this.addOption((new MenuOption("No")).onSelect(() -> {
			this.setResult(false);
			return MenuState.CLOSE;
		}));
	}
	
	public boolean resultWasYes() {
		return this.getResult();
	}
	
	public boolean resultWasNo() {
		return !this.getResult();
	}

}
