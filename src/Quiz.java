import java.util.ArrayList;
import java.util.*;

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
    ArrayList<Question> questions;
    private int ID;
    private boolean scrambled;

    //Quiz constructor.
    public Quiz(String name, String author, int numQuestions, int ID, ArrayList<Question> questions, String quizType, boolean scrambled) {
        this.name = name;
        this.author = author;
        this.numQuestions = numQuestions;
        this.quizType = quizType;
        this.ID = ID;
        this.scrambled = scrambled;
        this.questions = questions;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public void setScrambled(boolean scrambled) {
        this.scrambled = scrambled;
    }

    public boolean isScrambled() { return scrambled; }

    public void scrambleQuestions() {
        Collections.shuffle(questions);
    }

    public String toString() {
        String s = "Quiz name: " + name + ", Author: " + author;
        s+= ", ID: " + ID + ", Number of Questions: " + numQuestions + ".";
        return s;
    }


}
