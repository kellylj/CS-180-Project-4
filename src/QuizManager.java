import java.util.ArrayList;
/**
 * Stores a list of every created quiz, which is instantiated every time the program is run
 * <p>
 * Quiz Manager manages the list of quizzes, and provides methods for searching through them
 * <p>
 *
 *
 * @author Liam Kelly
 * @see Manager
 */
public class QuizManager implements Manager {

	LearningManagementSystem lms;
	ArrayList<Quiz> quizList = new ArrayList<>();
	public QuizManager(LearningManagementSystem lms) {
		this.lms = lms;
	}

	@Override
	public void init() {

	}

	public void addQuiz(Quiz quiz) {
		quizList.add(quiz);
	}

	public void removeQuiz(int ID) {
		int startingListLength = quizList.size();
		for (int i = 0; i < quizList.size(); i++) {
			if (quizList.get(i).getID() == ID) {
				quizList.remove(i);
				i--;
			}
		}
		if (startingListLength == quizList.size()) {
			System.out.println("No quizzes were found with that ID. Please try again");
		}
	}

	public String listQuizzes() {
		String s = "";
		for (Quiz q : quizList) {
			s+= q.toString() + "\n";
		}
		if (quizList.size() == 0) {
			s += "No quizzes have been created";
		}
		return s;
	}

	public ArrayList<Quiz> searchQuizByName(String name) {
		ArrayList<Quiz> matchingQuizzes = new ArrayList<>();
		for (Quiz q : quizList) {
			if (q.getName().toLowerCase().contains(name.toLowerCase())) {
				matchingQuizzes.add(q);
			}
		}
		if (matchingQuizzes.size() == 0) {
			return null;
		}
		return matchingQuizzes;
	}

	public ArrayList<Quiz> searchQuizByAuthor(String author) {
		ArrayList<Quiz> matchingQuizzes = new ArrayList<>();
		for (Quiz q : quizList) {
			if (q.getAuthor().toLowerCase().contains(author.toLowerCase())) {
				matchingQuizzes.add(q);
			}
		}
		if (matchingQuizzes.size() == 0) {
			return null;
		}
		return matchingQuizzes;
	}

	public ArrayList<Quiz> searchQuizByID(int ID) {
		ArrayList<Quiz> matchingQuizzes = new ArrayList<>();
		for (Quiz q : quizList) {
			if (q.getID() == ID) {
				matchingQuizzes.add(q);
			}
		}
		if (matchingQuizzes.size() == 0) {
			return null;
		}
		return matchingQuizzes;
	}

	public void setQuizList(ArrayList<Quiz> quizList) {
		this.quizList = quizList;
	}

	public ArrayList<Quiz> getQuizList() {
		return quizList;
	}

	@Override
	public void exit() {

	}

}
