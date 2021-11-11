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

    public void setID(String ID) {
        this.id = ID;
    }

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
    public String toString() {
        return answer;
    }
}
