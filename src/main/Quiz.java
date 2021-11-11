package main;
import java.util.ArrayList;
import java.util.*;
/**
 * A quiz, which contains an arraylist of questions along with various attributes of the quiz
 * <p>
 * Each quiz contains all the necessary attributes of a quiz, along with getters and setters for each
 * ,so they can be used
 * <p>
 *
 *
 * @author Liam Kelly
 * @see QuizManager
 */
public class Quiz {
    private String name;
    private String author;
    private int numQuestions;
    private String quizType;
    ArrayList<Question> questions;
    private int ID;
    private boolean scrambled;

    public Quiz(String name, String author, int numQuestions, int ID, ArrayList<Question> questions, String quizType, boolean scrambled) {
        this.name = name;
        this.author = author;
        this.numQuestions = numQuestions;
        this.quizType = quizType;
        this.ID = ID;
        this.scrambled = scrambled;
        this.questions = questions;
    }
    /**
     * Returns ID of the quiz
     *
     * @return ID - quiz-specific identifier
     */
    public int getID() {
        return ID;
    }
    /**
     * Returns name of the quiz
     *
     * @return name - name given to the quiz by a teacher
     */
    public String getName() {
        return name;
    }
    /**
     * Returns author of the quiz
     *
     * @return author - the name of the teacher who created the quiz
     */
    public String getAuthor() {
        return author;
    }
    /**
     * Returns number of questions of the quiz
     *
     * @return numQuestions - the number of questions the quiz conatins
     */
    public int getNumQuestions() {
        return numQuestions;
    }
    /**
     * Returns type of the quiz
     *
     * @return quizType - the type of quiz (T/F, multiple choice, mixed)
     */
    public String getQuizType() {
        return quizType;
    }
    /**
     * Sets quiz name
     *
     * @param name - the new name of the quiz
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets quiz author
     *
     * @param author - the new name of the author of the quiz
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * Sets quiz ID
     *
     * @param ID - the new ID of the quiz
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Sets quiz type
     *
     * @param quizType - the new question type of the quiz
     */
    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
    /**
     * Sets number of questions fo the quiz
     *
     * @param numQuestions - the new number of questions the quiz contains
     */
    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }
    /**
     * Sets quiz scrambled boolean
     *
     * @param scrambled - whether the teacher wants the quiz questions to be in random order
     */
    public void setScrambled(boolean scrambled) {
        this.scrambled = scrambled;
    }
    /**
     * Returns true if the quiz needs to be scrambled
     *
     * @return scrambled - whether the quiz questions should be in random order
     */
    public boolean isScrambled() { return scrambled; }
    /**
     * Randomized the order of questions in the questions arrayList
     * <p>
     * Will be called in the UI before displaying the quiz for an individual instance, to ensure questions
     * are ordered differently between students
     *
     */
    public void scrambleQuestions() {
        Collections.shuffle(questions);
    }
    /**
     * Returns a synopsis of the attributes of a quiz
     *
     * Used to display the list of all quizzes
     *
     * @return quizDescription - a synopsis of this individual quiz
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    public String toString() {
        String quizDescription = "Quiz name: " + name + ", Author: " + author;
        quizDescription+= ", ID: " + ID + ", Number of Questions: " + numQuestions + ".";
        return quizDescription;
    }


}
