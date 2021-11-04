package ui;

import main.Quiz;

/**
 * Lambda used for when a user has selected
 * a {@link Quiz} in {@link MenuQuizList}.
 * 
 * @author Isaac Fleetwood
 * @see MenuQuizList
 */
public interface RunnableSelectQuiz {
	public MenuState selectQuiz(Quiz quiz);
}