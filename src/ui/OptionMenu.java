package ui;

import java.util.ArrayList;

import main.UIManager;
import utils.ANSICodes;
import utils.NumberUtils;

public class OptionMenu extends Menu {

	UIManager uiManager;
	
	ArrayList<String> headings;
	ArrayList<MenuOption> options;
	Runnable callbackOnHeadingPrint;

	public OptionMenu(UIManager uiManager) {
		this.uiManager = uiManager;
		options = new ArrayList<MenuOption>();
		headings = new ArrayList<String>();
	}

	public OptionMenu addHeading(String heading) {
		headings.add("\n" + ANSICodes.BOLD + heading + ANSICodes.RESET);
		return this;
	}

	public OptionMenu setHeadings(ArrayList<String> headings) {
		this.headings = headings;
		return this;
	}

	public OptionMenu addSubheading(String subheading) {
		headings.add(subheading);
		return this;
	}

	public OptionMenu addOption(MenuOption option) {
		this.options.add(option);
		return this;
	}

	public OptionMenu onHeadingPrint(Runnable callbackOnHeadingPrint) {
		this.callbackOnHeadingPrint = callbackOnHeadingPrint;
		return this;
	}

	@Override
	public void runMenu() {

		for (String heading : headings) {
			System.out.println(heading);
		}
		if (this.callbackOnHeadingPrint != null)
			this.callbackOnHeadingPrint.run();
		// Copy references, so that they can be safely filtered.
		ArrayList<MenuOption> visibleOptions = new ArrayList<MenuOption>(options);
		visibleOptions.removeIf((option) -> {
			return !option.isVisible();
		});

		for (int i = 0; i < visibleOptions.size(); i++) {
			MenuOption option = visibleOptions.get(i);
			int optionNumber = i + 1;
			System.out.println(
					ANSICodes.CYAN + ANSICodes.BOLD + optionNumber + ": " + ANSICodes.RESET + option.getOptionString());
		}
		MenuOption option = null;
		while (option == null) {
			String input = uiManager.getScanner().nextLine();
			if (!NumberUtils.isInteger(input)) {
				System.out.println("Please use a valid integer when selecting an option.");
				continue;
			}
			int i = Integer.parseInt(input);
			if (i <= 0 || i > visibleOptions.size()) {
				System.out.println("Please select a valid option.");
				continue;
			}
			option = visibleOptions.get(i - 1);
			break;
		}
		menuState = option.onSelect();
	}

}