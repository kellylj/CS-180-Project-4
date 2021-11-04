package main;
/**
 * 
 * Main class that ties all the systems together.
 * <p>
 * Initializes each manager and provides access to them.
 * <p>
 * When the program has finished, it safely exits all managers
 * to clean up any loose ends.
 * 
 * @author Isaac Fleetwood
 */
public class LearningManagementSystem {
	
	private UIManager uiManager;
	private UserFileManager userFileManager;
	private QuizFileManager quizFileManager;
	private UserManager userManager;
	private QuizManager quizManager;
	
	/**
	 * Main method. Initializes the LMS instance
	 * and runs the program.
	 */
	public static void main(String[] args) {
		LearningManagementSystem lms = new LearningManagementSystem();
		lms.init();
		lms.run();
		lms.exit();
	}
	
	/**
	 * Instantiates each manager and passes them
	 * an instance of LMS to be able to access 
	 * all other managers.
	 */
	private LearningManagementSystem() {
		uiManager = new UIManager(this);
		userFileManager = new UserFileManager(this);
		quizFileManager = new QuizFileManager(this);
		userManager = new UserManager(this);
		quizManager = new QuizManager(this);
	}

	/**
	 * Initializes each manager before the user can
	 * interact with the program.
	 */
	private void init() {
		uiManager.init();
		userFileManager.init();
		quizFileManager.init();
		userManager.init();
		quizManager.init();
	}
	
	/**
	 * Runs the program. UIManager will
	 * run the UI loop here until the UI
	 * exits.
	 */
	private void run() {
		uiManager.run();
	}
	
	/**
	 * Notifies all the managers that
	 * the program is exiting.
	 */
	private void exit() {
		uiManager.exit();
		userFileManager.exit();
		quizFileManager.exit();
		userManager.exit();
		quizManager.exit();
	}
	
	public UIManager getUIManager() {
		return uiManager;
	}

	public UserFileManager getUserFileManager() {
		return userFileManager;
	}

	public QuizFileManager getQuizFileManager() {
		return quizFileManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public QuizManager getQuizManager() {
		return quizManager;
	}
	
}
