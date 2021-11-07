/**
 *
 * Class that contains the details of a particular quiz
 *
 * @author Liam Kelly
 * @version 11/4/21
 */
public class Quiz {
    private String name;
    private String author;
    private int numQuestions;
    private String quizType;
    //private Question[] questions;
    //private Answer[] answers;
    private int ID;
    private boolean scrambled;

    //Quiz constructor.
    //TODO: Once question/answer classes are made, add lists of each to constructor
    public Quiz(String name, String author, int numQuestions, int ID, String quizType, boolean scrambled) {
        this.name = name;
        this.author = author;
        this.numQuestions = numQuestions;
        this.quizType = quizType;
        this.ID = ID;
        this.scrambled = scrambled;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public String getQuizType() {
        return quizType;
    }

    public boolean isScrambled() { return scrambled; }

    public void scrambleQuestions() {

    }

    public String toString() {
        String s = "Quiz name: " + name + ", author: " + author;
        s+= ", ID: " + ID + ", Number of Questions: " + numQuestions + ".";
        return s;
    }


}
