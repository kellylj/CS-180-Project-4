import java.util.ArrayList;

public class Question {
    ArrayList<Answer> answers;
    String question;

    public Question(ArrayList<Answer> answers, String question) {
        this.answers = answers;
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public String toString() {
        String s = question;
        for (Answer answer : answers) {
            s += answer.toString() + "\n";
        }
        return s;
    }
}
