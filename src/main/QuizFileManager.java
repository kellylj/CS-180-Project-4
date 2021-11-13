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
		//TODO: change filepath, determine final separating characters
		ArrayList<Quiz> tempQuizzes = new ArrayList<>();
		String path = ""; // Change path to the path of the file that stores the quizzes
		ArrayList<String> contents = fw.readFile(path);

		if (contents == null) {
			return tempQuizzes;
		}

		for (int i = 0; i < contents.size(); i++) {
			String[] components = contents.get(i).split(";", 8); //A ";" (semicolon) is used to separate the parts of a quiz
			String name = components[0];
			String author = components[1];
			int numQuestions = Integer.parseInt(components[2]);
			ArrayList<Question> questions = this.readQuestions(components[3], numQuestions); //format the list of questions
			int id = Integer.parseInt(components[4]);
			boolean scrambled = Boolean.parseBoolean(components[5]);
			String course = components[6];
			tempQuizzes.add(new Quiz(name, author, numQuestions, id, questions, scrambled, course));
		}
		return tempQuizzes;
	}

	public ArrayList<Question> readQuestions(String questionList, int numQuestions) {
		ArrayList<Question> questions = new ArrayList<>();

		String[] list = questionList.split(",", numQuestions); //A "," (comma) is used to separate the different questions in a quiz

		for (int i = 0; i < numQuestions; i++) {
			String[] questionParts = list[i].split("/", 3); //A "/" (forward slash) is used to separate the answers from the question asked
			ArrayList<Answer> answers = this.readAnswers(questionParts[0]);
			String question = questionParts[1];
			int id = Integer.parseInt(questionParts[2]);
			questions.add(new Question(answers, question, id));
		}

		return questions;
	}

	public ArrayList<Answer> readAnswers(String answerList) {
		ArrayList<Answer> answers = new ArrayList<>();

		String[] list = answerList.split("-"); //A "-" (dash) is used to separate the answers from each other

		for (int i = 0; i < list.length; i++) {
			String[] answerParts = list[i].split("_", 4); //An "_" (underscore) is used to separate the parts of an answer
			String answer = answerParts[0];
			boolean correct = Boolean.parseBoolean(answerParts[1]);
			int points = Integer.parseInt(answerParts[2]);
			int id = Integer.parseInt(answerParts[3]);
			answers.add(new Answer(answer, correct, points, id));
		}
		return answers;
	}

	public boolean writeQuizzes() {
		//TODO: Determine final filepath and separator characters
		ArrayList<String> writableQuizzes = new ArrayList<>();
		String path = "";

		for (int i = 0; i < quizzes.size(); i++) {
			String name = quizzes.get(i).getName();
			String author = quizzes.get(i).getAuthor();
			int numQuestions = quizzes.get(i).getNumQuestions();
			String questions = formatQuestions(quizzes.get(i).getQuestions());
			int id = quizzes.get(i).getId();
			boolean scrambled = quizzes.get(i).isScrambled();
			String course = quizzes.get(i).getCourse();
			String addon = String.format("%s;%s;%s;%s;%s;%s;%s", name, author, numQuestions, questions, id, scrambled, course);
			writableQuizzes.add(addon);
		}

		boolean success = fw.writeFile(path, writableQuizzes);
		return success;
	}

	public String formatQuestions(ArrayList<Question> questions) {
		String retVal = "";
		for (int i = 0; i < questions.size(); i++) {
			String answers = formatAnswers(questions.get(i).getAnswers());
			String question = questions.get(i).getQuestion();
			String id = Integer.toString(questions.get(i).getId());
			if (i == questions.size() - 1) {
				retVal += String.format("%s/%s/%s", answers, question, id);
			} else {
				retVal += String.format("%s/%s/%s,", answers, question, id);
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
				retVal += String.format("%s_%s_%s_%s", answer, correct, points, id);
			} else {
				retVal += String.format("%s_%s_%s_%s-", answer, correct, points, id);
			}
		}
		return retVal;
	}

	public void importQuiz(String path) {
		//TODO: import quiz, return success boolean;
		String user = lms.getUIManager().getCurrentUser().getName(); //use for author name
		//use lms.getQuizManager().addQuiz(new Quiz(stuff));
	}

	public void setQuizzes(ArrayList<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

}
