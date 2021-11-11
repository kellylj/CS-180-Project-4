import java.util.ArrayList;
/**
 * Stores one Question, including multiple answers
 * <p>
 * Contains an ArrayList of answers, along with the string of the question to be asked.
 * <p>
 *
 *
 * @author Liam Kelly
 * @see Quiz
 */
public class Question {
    ArrayList<Answer> answers;
    String question;

    public Question(ArrayList<Answer> answers, String question) {
        this.answers = answers;
        this.question = question;
    }
    /**
     * Returns the list of possible answers
     *
     * @return answers - a list of all answers to the question
     */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }
    /**
     * Returns the question as it will be displayed to the user
     *
     * @return s - A string of the question, followed by the list of each possible answer
     */
    public String toString() {
        String s = question;
        for (Answer answer : answers) {
            s += answer.toString() + "\n";
        }
        return s;
    }
}
