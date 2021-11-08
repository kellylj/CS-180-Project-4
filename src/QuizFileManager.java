import java.util.ArrayList;

public class QuizFileManager implements Manager {

	LearningManagementSystem lms;
	private ArrayList<Quiz> quizzes;
	
	public QuizFileManager(LearningManagementSystem lms) {
		this.lms = lms;
		this.quizzes = this.readQuizzes();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		//lms.getQuizManager().setQuizzes();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		//lms.getQuizManager().getQuizzes();
	}

	public ArrayList<Quiz> readQuizzes() {
		//TODO: make method
		return null;
	}

	public void writeQuizzes(ArrayList<Quiz> quizzes) {
		//TODO: write class, maybe make return a boolean
	}

	public void importQuiz(String path) {
		//TODO: import quiz;
	}

}
