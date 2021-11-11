import java.util.ArrayList;

public class GradedQuiz {
    private Quiz quiz;
    private Student student;
    private ArrayList<Question> questions;
    private ArrayList<Answer> answers;

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

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }


}
