package main;
import java.util.ArrayList;
import java.util.*;
/**
 * A quiz, which contains an arraylist of questions along with various attributes of the quiz
 * <p>
 * Each quiz contains all the necessary attributes of a quiz, along with getters and setters for each
 * , so they can be used
 * <p>
 *
 *
 * @author Liam Kelly
 * @see QuizManager
 */
public class Quiz implements Listable{
    private String name;
    private String author;
    ArrayList<Question> questions;
    private int id;
    private boolean scrambled;
    private String course;

    public Quiz(String name, String author, int id, ArrayList<Question> questions,  boolean scrambled, String course) {
        this.name = name;
        this.author = author;
        this.id = id;
        this.scrambled = scrambled;
        this.questions = questions;
        this.course = course;
    }
    public Quiz(LearningManagementSystem lms, String name, String course) {
        this.name = name;
        this.author = lms.getUIManager().getCurrentUser().getName();
        this.id = lms.getQuizManager().getUniqueID();
        this.scrambled = false;
        this.questions = new ArrayList<>();
        this.course = course;
    }
    /**
     * Creates a unique ID for a new question
     * <p>
     * Gets the largest id from the list and adds 1 to ensure the id is unique
     *
     * @return max - a unique id for a new question object
     */
    public int generateUniqueQuestionId() {
        int max = 0;
        for (Question q: questions) {
            if (q.getId() > max) {
                max = q.getId();
            }
        }
        return max + 1;
    }
    /**
     * Returns id of the quiz
     *
     * @return id - quiz-specific identifier
     */
    public int getId() {
        return id;
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
     * @param id - the new ID of the quiz
     */
    public void setID(int id) {
        this.id = id;
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
     * Returns the name of the quiz
     *
     * @return name - a string containing the quiz's name
     */
    public String getListName() {
        return name;
    }
    /**
     * Returns the name of the course the quiz is a part of
     *
     * @return course - A string containing the course name
     */
    public String getCourse() {
        return course;
    }
    /**
     * Sets the course name for the quiz
     *
     * @param course - String containing the name of the course the quiz is a part of
     */
    public void setCourse(String course) {
        this.course = course;
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
        String quizDescription = "Quiz name: " + name + "\nAuthor: " + author;
        quizDescription+= "\nID: " + id + "\nCourse: " + course;
        return quizDescription;
    }


}
