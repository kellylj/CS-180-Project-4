package main;
/**
 * 
 * Main class that ties all the systems together.
 * Initializes each manager and provides access to them.
 * When the UI has exited, it safely exits all managers
 *   to clean up any loose ends.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
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
	public LearningManagementSystem() {
		userManager = new UserManager(this);
		quizManager = new QuizManager(this);
		userFileManager = new UserFileManager(this);
		quizFileManager = new QuizFileManager(this);
		uiManager = new UIManager(this);
	}

	/**
	 * Initializes each manager before the user can
	 * interact with the program.
	 */
	public void init() {
		userManager.init();
		quizManager.init();
		userFileManager.init();
		quizFileManager.init();
		uiManager.init();
	}
	
	/**
	 * Runs the program. UIManager will
	 * run the UI loop here until the UI
	 * exits.
	 */
	public void run() {
		uiManager.run();
	}
	
	/**
	 * Notifies all the managers that
	 * the program is exiting.
	 */
	public void exit() {
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
