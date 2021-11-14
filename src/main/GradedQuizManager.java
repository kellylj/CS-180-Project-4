package main;
import java.util.ArrayList;
import java.util.Random;
/**
 * Manager for the Graded quizzes
 *
 * @author Sean Lee
 * @see Manager
 */
public class GradedQuizManager implements Manager {

    LearningManagementSystem lms;
    ArrayList<GradedQuiz> gradedQuizList = new ArrayList<>();
    public GradedQuizManager(LearningManagementSystem lms) {
        this.lms = lms;
    }


    public void init() {

    }

    public void exit() {

    }

    public void addGradedQuiz(GradedQuiz gradedQuiz) {
        gradedQuizList.add(gradedQuiz);
    }

    /**
     * Removes a graded quiz from the list of graded quiz
     * @param ID of quiz you want to remove
     */
    public void removeQuiz(int ID) {
        int startingListLength = gradedQuizList.size();
        for (int i = 0; i < gradedQuizList.size(); i++) {
            if (gradedQuizList.get(i).getID().equals(Integer.toString(ID))) {
                gradedQuizList.remove(i);
                i--;
            }
        }
        if (startingListLength == gradedQuizList.size()) {
            System.out.println("No graded quizzes were found with that ID. Please try again");
        }
    }

    /**
     * Sets the graded Quiz list with input
     * @param gradedQuizList of graded quizzes
     */
    public void setGradedQuiz(ArrayList<GradedQuiz> gradedQuizList) {
        this.gradedQuizList = gradedQuizList;
    }

    /**
     *
     * @return List of graded quizzes
     */
    public String listGradedQuizzes() {
        StringBuilder quizDescriptions = new StringBuilder();
        for (GradedQuiz q : gradedQuizList) {
            quizDescriptions.append(q.toString()).append("\n");
        }
        if (gradedQuizList.size() == 0) {
            quizDescriptions.append("No quizzes have been created");
        }
        return quizDescriptions.toString();
    }

    /**
     *
     * @param course String inputted
     * @return list of quizzes that contains the input String in course
     */
    public ArrayList<GradedQuiz> searchGradedQuizzesByCourse(String course) {
        ArrayList<GradedQuiz> matchingQuizzes = new ArrayList<>();
        for (GradedQuiz gradedQuiz : gradedQuizList) {
            if (lms.getQuizManager().searchQuizByID(Integer.getInteger(gradedQuiz.getID().substring(1))).getCourse().equals(course)) {
                matchingQuizzes.add(gradedQuiz);
            }
        }
        return matchingQuizzes;
    }

    public ArrayList<GradedQuiz> getGradedQuizList() {
        return gradedQuizList;
    }
}
