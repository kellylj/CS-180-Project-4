public class Answer {
    String answer;
    boolean isCorrect;
    int points;

    public Answer(String answer, boolean correct, int points) {
        this.answer = answer;
        this.isCorrect = correct;
        this.points = points;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public String toString() {
        String s = answer;
        return s;
    }
}
