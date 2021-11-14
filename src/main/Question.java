package main;
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
public class Question implements Listable{
    ArrayList<Answer> answers;
    String question;
    int id;
    String questionType;

    public Question(ArrayList<Answer> answers, String question, int id, String questionType) {
        this.answers = answers;
        this.question = question;
        this.id = id;
        this.questionType = questionType;
    }
    /**
     * Returns a unique id for an answer
     *
     * @return max - a new unique id
     */
    public int generateUniqueAnswerId() {
        int max = 0;
        for (Answer a: answers) {
            if (a.getId() > max) {
                max = a.getId();
            }
        }
        return max + 1;
    }
    /**
     * Returns the question string, with a limit of 20 characters
     *
     * @return retVal - question string, capped at 20 characters
     */
    public String getListName() {
        String retVal = question;
        if (retVal.length() > 50) {
            retVal = retVal.substring(0, 50);
        }
        return retVal;
    }
    /**
     * Returns the list of answers
     *
     * @return answers - the list of answers to this question
     */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }
    /**
     * Returns the String representation of the question
     *
     * @return question - The question string
     */
    public String getQuestion() {
        return question;
    }
    /**
     * Changes the quiz ID to a new ID
     *
     * @param id - the new unique id for the quiz
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the unique quiz id
     *
     * @return id - the unique id for the quiz
     */
    public int getId() {
        return id;
    }
    /**
     * Returns the question type
     *
     * @return questionType - the type of question
     */
    public String getQuestionType() {
        return questionType;
    }
    /**
     * Returns the question type
     *
     * @param questionType - the type of question
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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
