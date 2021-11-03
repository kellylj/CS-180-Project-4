
public class LearningManagementSystem {

	private UIManager uiManager;
	private UserFileManager userFileManager;
	private QuizFileManager quizFileManager;
	private UserManager userManager;
	private FileManager fileManager;
	
	public static void main(String[] args) {
		LearningManagementSystem lms = new LearningManagementSystem();
		lms.init();
		lms.start();
	}
	
	public LearningManagementSystem() {
		uiManager = new UIManager(this);
		userFileManager = new UserFileManager(this);
		quizFileManager = new QuizFileManager(this);
		userManager = new UserManager(this);
		fileManager = new FileManager(this);
	}

	public void init() {
		uiManager.init();
		userFileManager.init();
		quizFileManager.init();
		userManager.init();
		fileManager.init();
	}
	
	public void start() {
		uiManager.start();
	}
	
	public UIManager getUiManager() {
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

	public FileManager getFileManager() {
		return fileManager;
	}
	
	
	
}
