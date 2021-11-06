package ui;

import java.util.ArrayList;

import main.LearningManagementSystem;
import main.Quiz;

/**
 * {@link OptionMenu} for selecting a {@link Quiz} object.
 * <p>
 * Uses pages to show only a certain number of quizzes at a time,
 * and allows the user to change the page to show different quizzes.
 * <p>
 * See {@link MenuQuizList#MenuQuizList(LearningManagementSystem, RunnableSelectQuiz)} for creating the menu.
 * <p>
 * Runs {@link RunnableSelectQuiz#selectQuiz(Quiz)} whenever the quiz is selected.
 * <p>
 * Note: It is possible a {@link Quiz} isn't selected if a user chooses "Exit"
 * 
 * @author Isaac Fleetwood
 *
 */
public class MenuQuizList extends OptionMenuWithResult<Quiz> {

	private static final int AMT_QUIZ_PER_PAGE = 5;

	LearningManagementSystem lms;
	RunnableSelectQuiz runnableSelectQuiz;
	int page = 0;
	
	/**
	 * Used to create the quiz list menu.
	 * 
	 * @param lms Used for accessing {@link QuizManager} to get the list of quizzes.
	 * @param runnableSelectQuiz Used as a callback function for whenever a quiz is selected. 
	 * 	{@link RunnableSelectQuiz#selectQuiz(Quiz)} is ran whenever a quiz is selected.
	 * @see RunnableSelectQuiz
	 */
	public MenuQuizList(LearningManagementSystem lms, RunnableSelectQuiz runnableSelectQuiz) {
		super(lms.getUIManager());
		this.page = 0;
		this.runnableSelectQuiz = runnableSelectQuiz;
	}

	@Override
	public void runMenu() {
		// Clear options so that all options are new.
		this.options.clear();
		
		// Add "Previous Page" option, which causes the page to decrease.
		this.addOption((new MenuOption("Previous Page")).addVisibilityCondition(() -> {
			return this.page > 0;
		}).onSelect(() -> {
			this.page -= 1;
			return MenuState.RESTART;
		}));
		
		// TODO QuizManager get quizzes from QuizManager here
		// lms.getQuizManager().getQuizzes();
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		for(int i = 0; i < 100; i++) quizzes.add(new Quiz("Test", "Turkstra", 0, i, "Multiple Choice"));
		
		for (int i = 0; i < AMT_QUIZ_PER_PAGE; i++) {
			int j = page * AMT_QUIZ_PER_PAGE + i;
			if (j < 0 || j >= quizzes.size())
				continue;
			// TODO Use Quiz Name here.
			final Quiz q = quizzes.get(j);
			this.addOption((new MenuOption("Quiz " + (j+1))).onSelect(() -> {
				this.setResult(q);
				return MenuState.CLOSE;
			}));
		}
		
		// Add Next Page option, which increases the page, but only if there's more quizzes to see.
		this.addOption((new MenuOption("Next Page")).addVisibilityCondition(() -> {
			return quizzes.size() > (page+1) * AMT_QUIZ_PER_PAGE;
		}).onSelect(() -> {
			this.page += 1;
			return MenuState.RESTART;
		}));
		
		// Add Exit option, which closes the menu.
		this.addOption((new MenuOption("Exit")).onSelect(() -> {
			this.setResult(null);
			return MenuState.CLOSE;
		}));
		
		// Now that all the options are added, run the menu.
		super.runMenu();
		
		// If the menu should be restart (page was changed), return, which will cause the menu to rerun.
		if (menuState.equals(MenuState.RESTART))
			return;
		// If the result is null, return, which will cause the page to exit without doing anything.
		if (this.getResult() == null)
			return;
		
		// Otherwise, a quiz was selected, so run the callback with that quiz.
		menuState = this.runnableSelectQuiz.selectQuiz(this.getResult());
	}

}
