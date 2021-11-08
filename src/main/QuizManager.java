package main;

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

	public void addQuiz(Quiz quiz) {
		quizList.add(quiz);
	}

	public void removeQuiz() {

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

	public String searchQuizByName(String name) {
		String s = "";
		for (Quiz q : quizList) {
			if (q.getName().toLowerCase().contains(name.toLowerCase())) {
				s += q.toString() + "\n";
			}
		}
		if (s.equals("")) {
			s+= "No quizzes found with that name";
		}
		return s;
	}

	public String searchQuizByAuthor(String author) {
		String s = "";
		for (Quiz q : quizList) {
			if (q.getAuthor().toLowerCase().contains(author.toLowerCase())) {
				s += q.toString() + "\n";
			}
		}
		if (s.equals("")) {
			s+= "No quizzes found with that author";
		}
		return s;
	}

	public String searchQuizByID(int ID) {
		String s = "";
		for (Quiz q : quizList) {
			if (q.getID() == ID) {
				s += q.toString() + "\n";
			}
		}
		if (s.equals("")) {
			s+= "No quizzes found with that ID";
		}
		return s;
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	public ArrayList<Quiz> getQuizzes() {
		// TODO Use actual quiz array.
		Quiz quiz = new Quiz("Quiz Name", "Teacher", 0, 0, "TEST");
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		for(int i = 0; i < 13; i++) {
			quizzes.add(quiz);
		}
		return quizzes;
	}

}
