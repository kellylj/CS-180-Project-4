package main;
import java.util.ArrayList;
import java.util.Random;
/**
 * Stores a list of every created quiz, which is instantiated every time the program is run
 * <p>
 * Quiz Manager manages the list of quizzes, and provides methods for searching through them
 *
 *
 * @author Liam Kelly
 * @version 11/14/21
 * @see Manager
 */
public class QuizManager implements Manager {
	Random rand = new Random();
	LearningManagementSystem lms;
	ArrayList<Quiz> quizList = new ArrayList<>();
	public QuizManager(LearningManagementSystem lms) {
		this.lms = lms;
	}
	/**
	 * Run whenever the program is initialized
	 */
	@Override
	public void init() {

	}
	/**
	 * Adds a quiz to the quizList
	 *
	 * @param quiz - A new quiz to be added to the list
	 */
	public void addQuiz(Quiz quiz) {
		quizList.add(quiz);
	}

	/**
	 * Removes a quiz from the list
	 *<p>
	 * Removes a quiz matching a specific ID from the list of all quizzes.
	 * If none are removed, displays a message
	 *
	 * @param id - ID of the quiz that needs to be removed
	 */
	public void removeQuiz(int id) {
		int startingListLength = quizList.size();
		for (int i = 0; i < quizList.size(); i++) {
			if (quizList.get(i).getId() == id) {
				quizList.remove(i);
				i--;
			}
		}
		if (startingListLength == quizList.size()) {
			System.out.println("No quizzes were found with that ID. Please try again");
		}
	}

	/**
	 * Returns a list with descriptions of every created quiz
	 *<p>
	 * Lists all quizzes' toStrings.  Return a message if no quizzes have been created.
	 *
	 * @return quizDescriptions - A String of all created quizzes
	 */
	public String listQuizzes() {
		String quizDescriptions = "";
		for (Quiz q : quizList) {
			quizDescriptions += q.toString() + "\n";
		}
		if (quizList.size() == 0) {
			quizDescriptions += "No quizzes have been created";
		}
		return quizDescriptions;
	}
	/**
	 * Searches for quizzes matching a specified course
	 *<p>
	 * Returns the list of quizzes if any are found that match.  If none match, returns null.
	 * Not case-sensitive.
	 *
	 * @return matchingQuizzes - a list of quizzes that match the specified course
	 * @param course - the quiz course to search for
	 */
	public ArrayList<Quiz> searchQuizByCourse(String course) {
		ArrayList<Quiz> matchingQuizzes = new ArrayList<>();
		for (Quiz q : quizList) {
			if (q.getCourse().toLowerCase().equals(course.toLowerCase())) {
				matchingQuizzes.add(q);
			}
		}
		return matchingQuizzes;
	}
	/**
	 * Searches for quizzes matching a specified name
	 *<p>
	 * Returns the list of quizzes if any are found that match.  If none match, returns null.
	 * Not case-sensitive.
	 *
	 * @return matchingQuizzes - a list of quizzes that match the specified name
	 * @param name - the quiz name to search for
	 */
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
	/**
	 * Searches for quizzes matching a specified author
	 *<p>
	 * Returns the list of quizzes if any are found that match.  If none match, returns null.
	 * Not case-sensitive.
	 *
	 * @return matchingQuizzes - a list of quizzes that match the specified name
	 * @param author - the author name to search for
	 */
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
	/**
	 * Searches for quizzes matching a specified ID
	 *<p>
	 * Returns the list of quizzes if any are found that match.  If none match, returns null
	 *
	 * @return matchingQuizzes - a list of quizzes that match the specified ID
	 * @param id - the ID to search for
	 */
	public Quiz searchQuizByID(int id) {
		for (Quiz q : quizList) {
			if (q.getId() == id) {
				return q;
			}
		}
		return null;
	}
	/**
	 * Returns the list of all courses created
	 * <p>
	 * If no courses have been created, it will return an empty ArrayList
	 *
	 * @return courses - an ArrayList of all created courses
	 */
	public ArrayList<String> getListOfCourses() {
		ArrayList<String> courses = new ArrayList<>();
		for (int i = 0; i < quizList.size(); i++) {
			String course = quizList.get(i).getCourse();
			if (!(courses.contains(course))) {
				courses.add(course);
			}

		}
		return courses;
	}
	/**
	 * Sets the quizList to a different quizList
	 *<p>
	 * Used in initialization of the program to copy quiz list from files
	 *
	 * @param quizList - the new list of quizzes
	 */
	public void setQuizList(ArrayList<Quiz> quizList) {
		this.quizList = quizList;
	}
	/**
	 * Creates a unique ID for instantiation of a new quiz
	 * <p>
	 * Checks to make sure the generated id hasn't been used before to prevent duplicate ids
	 *
	 * @return id - a unique id for a new quiz object
	 */
	public int getUniqueID() {
		int id = 0;
		boolean exists = true;
		while (exists) {
			exists = false;
			id = rand.nextInt(999999);
			for (int i = 0; i < quizList.size(); i++) {
				if (quizList.get(i).getId() == i) {
					exists = true;
				}
			}
		}
		return id;
	}
	/**
	 * Returns the list of all quizzes
	 *
	 * @return quizList - list of every created quiz
	 */
	public ArrayList<Quiz> getQuizList() {
		return quizList;
	}
	/**
	 * Run whenever the program is terminated
	 */
	@Override
	public void exit() {

	}

}
