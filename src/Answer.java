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

    public Answer(String answer, boolean correct, int points) {
        this.answer = answer;
        this.isCorrect = correct;
        this.points = points;
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
     * Returns answer string
     *
     * @return {@link #answer}
     */
    public String toString() {
        String s = answer;
        return s;
    }
}
