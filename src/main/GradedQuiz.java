package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * Graded Quiz that contains the information of a quiz that has been graded
 *
 * @author Sean Lee
 * @see GradedQuizManager
 */
public class GradedQuiz { // should students be able to take quiz multiple times
    private Quiz quiz;
    private Student student;
    private String submissionTime;
    private int quizID;
    private int studentID;
    private HashMap<Integer, Integer> map = new HashMap<>();

    public GradedQuiz(int quizID, int studentID) {
        this.quizID = quizID;
        this.studentID = studentID;
    }

    public GradedQuiz(int quizID, int studentID, HashMap<Integer, Integer> map, String submissionTime) {
        this.quizID = quizID;
        this.studentID = studentID;
        this.map = map;
        this.submissionTime = submissionTime;
    }

    /**
     * @return Hash Map of Graded Quizzes
     */
    public HashMap<Integer, Integer> getGradedQuizMap() {
        return this.map;
    }

    /**
     * Returns the ID of the quiz that got graded
     * @return Quiz ID
     */
    public int getQuizID() {
        return this.quizID;
    }

    /**
    * Returns the ID of the student that took the quiz
    * @return Student ID
     */
    public int getStudentID() {
        return this.studentID;
    }

    /**
     * Adds a question to the Hash Map of questions and answers
     * @param question Question that will be added
     * @param answer Answer that will be added
     */
    public void addQuestion(Question question, Answer answer) {
        map.put(question.getId(), answer.getId());
    }

    /**
     * Returns string of ID of GradedQuiz
     * @return String of Graded Quiz ID
     */
    public String getID() {
        return String.format("G%d", quizID);
    }

    /**
     * Adds a question to the Hash Map of questions and answers
     * @param questionID ID of question that will be added to hash map
     * @param answerID ID of answer that will be added to hash map
     */
    public void addQuestion(int questionID, int answerID) {
        map.put(questionID, answerID);
    }

    /**
     * Returns submission time of quiz
     * @return Submission time of quiz
     */
    public String getSubmissionTime() {
        return submissionTime;
    }

    /**
     * Sets the submission time of quiz
     * @param submissionTime Submission time of quiz
     */
    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }
}
