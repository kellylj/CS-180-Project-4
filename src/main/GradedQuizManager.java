package main;
import java.util.ArrayList;
import java.util.Random;

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

    public ArrayList<GradedQuiz> searchGradedQuizzesByCourse(String course) {
        ArrayList<GradedQuiz> matchingQuizzes = new ArrayList<>();
        for (GradedQuiz gradedQuiz : gradedQuizList) {
            if (gradedQuiz.getQuiz().getCourse().equals(course)) {
                matchingQuizzes.add(gradedQuiz);
            }
        }
        return matchingQuizzes;
    }

    public ArrayList<GradedQuiz> getGradedQuizList() {
        return gradedQuizList;
    }
}
