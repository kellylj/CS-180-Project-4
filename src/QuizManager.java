import java.util.ArrayList;

public class QuizManager implements Manager {

	LearningManagementSystem lms;
	ArrayList<Quiz> quizList = new ArrayList<>();
	public QuizManager(LearningManagementSystem lms) {
		this.lms = lms;
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
