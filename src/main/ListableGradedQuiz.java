package main;

/**
 * 
 * A wrapper class for Graded Quiz that is used for showing
 * graded quizzes in UI lists. Contains a graded quiz object
 * and a friendly name for the quiz that can safely be 
 * shown to the user.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 *
 */
public class ListableGradedQuiz implements Listable {

	private String nameUser;
	private String nameQuiz;
	private GradedQuiz gradedQuiz;
	
	public ListableGradedQuiz(LearningManagementSystem lms, GradedQuiz gradedQuiz) {
		try {
			this.nameUser = lms.getUserManager().getUserById(gradedQuiz.getStudentID()).getName();
			this.nameQuiz = lms.getQuizManager().searchQuizByID(gradedQuiz.getQuizID()).getName();
		} catch (NullPointerException e) {
			this.nameUser = "Unknown";
			this.nameQuiz = "Unknown";
		}
		this.gradedQuiz = gradedQuiz;
	}
	
	@Override
	public String getListName() {
		return this.nameUser + " - " + this.nameQuiz + " - " + this.gradedQuiz.getSubmissionTime();
	}

	public GradedQuiz getGradedQuiz() {
		return this.gradedQuiz;
	}
	
}
