package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GradedQuiz { // should students be able to take quiz multiple times
    private Quiz quiz;
    private Student student;
    private String submissionTime;
    private HashMap<Question,Answer> map = new HashMap<Question,Answer>();

    public GradedQuiz(Quiz quiz, Student student) {
        this.quiz = quiz;
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Student getStudent() {
        return student;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void addQuestion(Question question, Answer answer) {
        map.put(question, answer);
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getID() {
        return String.format("G%d", quiz.getId());
    }

    public int getTotalScore() {
        int total = 0;
        for (Map.Entry<Question, Answer> m: map.entrySet()) {
            total += m.getValue().getPoints();
        }
        return total;
    }
}
