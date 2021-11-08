import java.io.File;
import java.util.ArrayList;

public class QuizFileManager implements Manager {

	LearningManagementSystem lms;
	private ArrayList<Quiz> quizzes;
	private FileWrapper fw = new FileWrapper();
	
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
		//TODO: does it need to return? or does it need a class variable? (quizzes), change filepath, determine final separating characters
		String path = ""; // Change path to the path of the file that stores the quizzes
		ArrayList<String> contents = fw.readFile(path);

		for (int i = 0; i < contents.size(); i++) {
			String[] components = contents.get(i).split(";", 7); //A ";" (semicolon) is used to separate the parts of a quiz
			String name = components[0];
			String author = components[1];
			int numQuestions = Integer.parseInt(components[2]);
			String quizType = components[3];
			ArrayList<Question> questions = this.formatQuestions(components[4], numQuestions); //format the list of questions
			int id = Integer.parseInt(components[5]);
			boolean scrambled = Boolean.parseBoolean(components[6]);
			quizzes.add(new Quiz(name, author, numQuestions, id, questions, quizType, scrambled));
		}

		return quizzes;
	}

	public ArrayList<Question> formatQuestions(String questionList, int numQuestions) {
		ArrayList<Question> questions = new ArrayList<>();

		String[] list = questionList.split(",", numQuestions); //A "," (comma) is used to separate the different questions in a quiz

		for (int i = 0; i < numQuestions; i++) {
			String[] questionParts = list[i].split("/", 2); //A "/" (forward slash) is used to separate the answers from the question asked
			ArrayList<Answer> answers = this.formatAnswers(questionParts[0]);
			String question = questionParts[1];
			questions.add(new Question(answers, question));
		}

		return questions;
	}

	public ArrayList<Answer> formatAnswers(String answerList) {
		ArrayList<Answer> answers = new ArrayList<>();

		String[] list = answerList.split("-"); //A "-" (dash) is used to separate the answers from each other

		for (int i = 0; i < list.length; i++) {
			String[] answerParts = list[i].split("_", 3); //An "_" (underscore) is used to separate the parts of an answer
			String answer = answerParts[0];
			boolean correct = Boolean.parseBoolean(answerParts[1]);
			int points = Integer.parseInt(answerParts[2]);
			answers.add(new Answer(answer, correct, points));
		}

		return answers;
	}

	public void writeQuizzes(ArrayList<Quiz> quizzes) {
		//TODO: write class, maybe make return a boolean
	}

	public void importQuiz(String path) {
		//TODO: import quiz;
	}

}
