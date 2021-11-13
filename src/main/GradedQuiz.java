package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GradedQuiz { // should students be able to take quiz multiple times
    private Quiz quiz;
    private Student student;
    private String submissionTime;
    private int quizID;
    private int studentID;
    private HashMap<Integer, Integer> map = new HashMap<>();

//    public GradedQuiz(Quiz quiz, Student student) {
//        this.quiz = quiz;
//        this.student = student;
//    }

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

//    public Quiz getQuiz() {
//        return quiz;
//    }
//
//    public Quiz getQuiz(QuizManager quizManager) {
//        return quizManager.searchQuizByID(quizID).get(0); // there should only be one quiz that matches the id
//    }
//
//    public Student getStudent() {
//        return student;
//    }
//
//
//    public String getID() {
//        return String.format("G%d", quiz.getId());
//    }

    public void addQuestion(Question question, Answer answer) {
        map.put(question.getId(), answer.getId());
    }

    public String getID() {
        return String.format("G%d", quizID);
    }

    public void addQuestion(int questionID, int answerID) {
        map.put(questionID, answerID);
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }
}
