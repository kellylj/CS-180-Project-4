package main;
/**
 * Stores one answer to a question
 * <p>
 * Contains a string for the text of the answer that is to be displayed, along with whether the
 * answer is correct or incorrect
 * <p>
 *
 *
 * @author Liam Kelly
 * @see Question
 */
public class Answer {
    String answer;
    boolean isCorrect;
    int points;
    String id;
    public Answer(String answer, boolean correct, int points, String id) {
        this.answer = answer;
        this.isCorrect = correct;
        this.points = points;
        this.id = id;
    }

    /**
     * Returns if the answer is correct or not
     *
     * @return {@link #isCorrect}
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * Returns number of points
     *
     * @return {@link #points}
     */
    public int getPoints() {
        return points;
    }
    /**
     * Sets the new question id
     *
     * @param id - the new unique id for the answer
     */
    public void setID(String id) {
        this.id = id;
    }
    /**
     * Returns the question's unique id
     *
     * @return id - the unique id for the question
     */
    public String getId() {
        return id;
    }
    /**
     * Returns answer string
     *
     * @return {@link #answer}
     */
    public String getAnswer() {
        return answer;
    }
    /**
     * Returns the answer as a string for the answer class's toString
     *
     * @return answer - the string representation of the answer
     */
    public String toString() {
        return answer;
    }
}
