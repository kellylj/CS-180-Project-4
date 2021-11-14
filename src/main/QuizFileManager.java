package main;
import java.io.File;
import java.nio.charset.StandardCharsets;
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
		lms.getQuizManager().setQuizList(quizzes);
	}

	@Override
	public void exit() {
		quizzes = lms.getQuizManager().getQuizList();
		this.writeQuizzes();
	}

	public ArrayList<Quiz> readQuizzes() {
		//TODO: determine final separating characters
		ArrayList<Quiz> tempQuizzes = new ArrayList<>();
		String path = "./data/quizzes.txt";
		ArrayList<String> contents = fw.readFile(path);

		if (contents == null) {
			return tempQuizzes;
		}

		for (int i = 0; i < contents.size(); i++) {
			String[] components = contents.get(i).split(";;", 8); //Two ";;" (semicolons) are used to separate the parts of a quiz
			String name = components[0];
			String author = components[1];
			ArrayList<Question> questions = this.readQuestions(components[2]); //format the list of questions
			int id = Integer.parseInt(components[3]);
			boolean scrambled = Boolean.parseBoolean(components[4]);
			String course = components[5];
			tempQuizzes.add(new Quiz(name, author, id, questions, scrambled, course));
		}
		return tempQuizzes;
	}

	public ArrayList<Question> readQuestions(String questionList) {
		ArrayList<Question> questions = new ArrayList<>();

		String[] list = questionList.split("::", -1); //Two "::" (colons) are used to separate the different questions in a quiz

		for (int i = 0; i < list.length; i++) {
			String[] questionParts = list[i].split("//", 4); //Two "//" (forward slashes) are used to separate the answers from the question asked
			ArrayList<Answer> answers = this.readAnswers(questionParts[0]);
			String question = questionParts[1];
			int id = Integer.parseInt(questionParts[2]);
			String questionType = questionParts[3];
			questions.add(new Question(answers, question, id, questionType));
		}

		return questions;
	}

	public ArrayList<Answer> readAnswers(String answerList) {
		ArrayList<Answer> answers = new ArrayList<>();

		String[] list = answerList.split("--"); //Two "--" (dashes) are used to separate the answers from each other

		for (int i = 0; i < list.length; i++) {
			String[] answerParts = list[i].split("__", 4); //Two "__" (underscores) are used to separate the parts of an answer
			String answer = answerParts[0];
			boolean correct = Boolean.parseBoolean(answerParts[1]);
			int points = Integer.parseInt(answerParts[2]);
			int id = Integer.parseInt(answerParts[3]);
			answers.add(new Answer(answer, correct, points, id));
		}
		return answers;
	}

	public boolean writeQuizzes() {
		//TODO: Determine final separator characters
		ArrayList<String> writableQuizzes = new ArrayList<>();
		String path = "./data/quizzes.txt";

		for (int i = 0; i < quizzes.size(); i++) {
			String name = quizzes.get(i).getName();
			String author = quizzes.get(i).getAuthor();
			String questions = formatQuestions(quizzes.get(i).getQuestions());
			int id = quizzes.get(i).getId();
			boolean scrambled = quizzes.get(i).isScrambled();
			String course = quizzes.get(i).getCourse();
			String addon = String.format("%s;;%s;;%s;;%s;;%s;;%s", name, author, questions, id, scrambled, course);
			writableQuizzes.add(addon);
		}

		return fw.writeFile(path, writableQuizzes);
	}

	public String formatQuestions(ArrayList<Question> questions) {
		String retVal = "";
		for (int i = 0; i < questions.size(); i++) {
			String answers = formatAnswers(questions.get(i).getAnswers());
			String question = questions.get(i).getQuestion();
			String id = Integer.toString(questions.get(i).getId());
			String questionType = questions.get(i).getQuestionType();
			if (i == questions.size() - 1) {
				retVal += String.format("%s//%s//%s//%s", answers, question, id, questionType);
			} else {
				retVal += String.format("%s//%s//%s//%s::", answers, question, id, questionType);
			}
		}
		return retVal;
	}

	public String formatAnswers(ArrayList<Answer> answers) {
		String retVal = "";
		for (int i = 0; i < answers.size(); i++) {
			String answer = answers.get(i).getAnswer();
			boolean correct = answers.get(i).isCorrect();
			int points = answers.get(i).getPoints();
			String id = Integer.toString(answers.get(i).getId());
			if (i == answers.size() - 1) {
				retVal += String.format("%s__%s__%s__%s", answer, correct, points, id);
			} else {
				retVal += String.format("%s__%s__%s__%s--", answer, correct, points, id);
			}
		}
		return retVal;
	}

	public Quiz importQuiz(String path, String name, String course) {
		String user = lms.getUIManager().getCurrentUser().getName(); //use for author name
		int quizId = lms.getQuizManager().getUniqueID();

		ArrayList<String> contents = fw.readImportFile(path);

		if (contents == null) {
			return null;
		}

		ArrayList<Question> questions = new ArrayList<>();

		for (int i = 0; i < contents.size(); i++) {
			String[] quizParts = contents.get(i).split("\n", -1);
			String[] questionParts = quizParts[0].split("/", 2);
			String question = questionParts[0];
			String questionType = questionParts[1];
			int questionId = i;
			ArrayList<Answer> answers = new ArrayList<>();
			for (int j = 1; j < quizParts.length; j++) {
				String[] answerParts = quizParts[j].split(";;", 3);
				String answer = answerParts[0];
				boolean isCorrect = Boolean.parseBoolean(answerParts[1]);
				int numPoints = Integer.parseInt(answerParts[2]);
				int answerId = i - 1;
				answers.add(new Answer(answer, isCorrect, numPoints, answerId));
			}
			questions.add(new Question(answers, question, questionId, questionType));
		}

		Quiz quiz = new Quiz(name, user, quizId, questions, false, course);

		return quiz;
	}

	public void setQuizzes(ArrayList<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

}
