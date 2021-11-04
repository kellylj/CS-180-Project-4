public class Quiz {
    private String name;
    private String author;
    private int numQuestions;
    private String quizType;
    private String[] questions;
    private int[] answers;
    private int ID;


    //Quiz constructor.
    //TODO: Once question/answer classes are made, add lists of each to constructor
    public Quiz(String name, String author, int numQuestions, int ID, String quizType) {
        this.name = name;
        this.author = author;
        this.numQuestions = numQuestions;
        this.quizType = quizType;
        this.ID = ID;

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

    public String toString() {
        String s = "Quiz name: " + name + ", author: " + author;
        s+= ", ID: " + ID + ", Number of Questions: " + numQuestions + ".";
        return s;
    }


}
