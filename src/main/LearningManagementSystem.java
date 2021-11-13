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
	private GradedQuizFileManager gradedQuizFileManager;
	private UserManager userManager;
	private QuizManager quizManager;
	private GradedQuizManager gradedQuizManager;
	
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
		userFileManager = new UserFileManager(this);
		quizManager = new QuizManager(this);
		quizFileManager = new QuizFileManager(this);
		gradedQuizManager = new GradedQuizManager(this);
		gradedQuizFileManager = new GradedQuizFileManager(this); // TODO Pass instance
		uiManager = new UIManager(this);
	}

	/**
	 * Initializes each manager before the user can
	 * interact with the program.
	 */
	public void init() {
		userManager.init();
		userFileManager.init();
		quizManager.init();
		quizFileManager.init();
		gradedQuizManager.init();
		gradedQuizFileManager.init();
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
		gradedQuizManager.exit();
		gradedQuizFileManager.exit();
		quizManager.exit();
		quizFileManager.exit();
		userManager.exit();
		userFileManager.exit();
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

	public GradedQuizFileManager getGradedQuizFileManager() {
		return gradedQuizFileManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public QuizManager getQuizManager() {
		return quizManager;
	}

	public GradedQuizManager getGradedQuizManager() {
		return gradedQuizManager;
	}
		
}
