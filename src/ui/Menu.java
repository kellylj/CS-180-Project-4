package ui;

/**
 * Abstract class that represents a menu in the user interface.
 * <p>
 * All menus will be a child of this class. When a menu should be opened, {@link #open()} should be ran, which will run {@link #runMenu()} until the {@link #menuState} is {@link MenuState.CLOSE}.
 * <p>
 * Subclasses will implement the {@link #runMenu()} method, and should not override the {@link #open()} unless necessary.
 * <p>
 * {@link #menuState} should be updated appropriately in {@link #runMenu()} depending on the desire of the Menu to stay open or not.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * @see InputMenu
 * @see OptionMenu
 */
public abstract class Menu {

	/**
	 * Current state of the menu.
	 * <p>
	 * Is used for determining if the menu should restart or close once an option is chosen.
	 * <p>
	 * Should be set inside {@link #runMenu()}
	 */
	MenuState menuState;
	
	public Menu() {
		this.menuState = MenuState.CLOSE;
	}

	/**
	 * Ran whenever the menu is opened.
	 */
	public void start() {
		// TODO: Figure out what needs to go here, if anything.
	}

	/**
	 * Opens the menu and holds the user until the menu exits.
	 * Runs {@link #runMenu()} until {@link #menuState} is set to {@link MenuState.CLOSE}
	 * <p>
	 * If you want to open the {@link Menu}, this is the method to run.
	 * 
	 * @see Menu#runMenu()
	 */
	public void open() {
		this.start();
		do {
			this.runMenu();
		} while (menuState.equals(MenuState.RESTART));
		this.end();
	}

	/**
	 * Print information to the user and prompts for user input.
	 * <p>
	 * Is implemented by all subclasses.
	 */
	public abstract void runMenu();

	/**
	 * Ran whenever the menu is finally closed.
	 */
	public void end() {
		// TODO: Figure out what needs to go here, if anything.
	}
	
}
