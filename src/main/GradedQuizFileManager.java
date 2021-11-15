package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Reads and writes files with graded quiz information for initialization and storage
 * <p>
 * Contains an ArrayList of graded quizzes from storage that will be passed to GradedQuizManager.
 * <p>
 *
 * @author Daniel Geva
 * @version 11/14/21
 * @see GradedQuizManager
 * @see FileWrapper
 */
public class GradedQuizFileManager implements Manager {

    LearningManagementSystem lms;
    private ArrayList<GradedQuiz> gradedQuizzes;
    private FileWrapper fw = new FileWrapper();

    public GradedQuizFileManager(LearningManagementSystem lms) {
        this.lms = lms;
        this.gradedQuizzes = this.readGradedQuizzes();
    }

    @Override
    public void init() {
        lms.getGradedQuizManager().setGradedQuiz(gradedQuizzes);
    } //Sets the GradedQuizManger's arraylist of graded quizzes

    @Override
    public void exit() {
        this.save();
    }

    public void save() {
        gradedQuizzes = lms.getGradedQuizManager().getGradedQuizList();
        this.writeGradedQuizzes();
    } //gets the altered arraylist of graded quizzes from GradedQuizManger and writes it to a file

    public ArrayList<GradedQuiz> readGradedQuizzes() {
        ArrayList<GradedQuiz> tempGradQuiz = new ArrayList<>();
        String path = "./data/gradedQuizzes.txt";
        ArrayList<String> contents = fw.readFile(path);

        if (contents == null) {
            return tempGradQuiz;
        }

        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).isBlank() || contents.get(i).isEmpty()) {
                continue;
            }
            String[] list = contents.get(i).split(";;", 4);
            //Two ";;" semicolons are used to separate the parts of a graded quiz
            int quizID = Integer.parseInt(list[0]);
            int studentID = Integer.parseInt(list[1]);
            HashMap<Integer, Integer> map = createHashmap(list[2]); //handles creating the Hashmap
            String submissionTime = list[3];
            tempGradQuiz.add(new GradedQuiz(quizID, studentID, map, submissionTime));
        }
        return tempGradQuiz;
    } //Reads the file that stores the graded quiz data and creates an arraylist of graded quizzes

    public HashMap<Integer, Integer> createHashmap(String contents) { //Used to create the Hashmap
        HashMap<Integer, Integer> map = new HashMap<>();
        String[] list = contents.split("//", -1);
        //Two "//" forward slashes are used to separate the key/value pairs from each other

        for (int i = 0; i < list.length; i++) {
            String[] parts = list[i].split(",,", 2); //Two ",," commas are used to separate the key and value
            Integer questionId = Integer.parseInt(parts[0]);
            Integer answerId = Integer.parseInt(parts[1]);
            map.put(questionId, answerId);
        }
        return map;
    }

    public boolean writeGradedQuizzes() { //Writes the arraylist of graded quizzes to a file for storage
        ArrayList<String> writableGradedQuizzes = new ArrayList<>();
        String path = "./data/gradedQuizzes.txt";

        for (int i = 0; i < gradedQuizzes.size(); i++) {
            int quizId = gradedQuizzes.get(i).getQuizID();
            int studentId = gradedQuizzes.get(i).getStudentID();
            String mapList = this.formatHashmap(gradedQuizzes.get(i).getGradedQuizMap());
            String submissionTime = gradedQuizzes.get(i).getSubmissionTime();
            writableGradedQuizzes.add(String.format("%d;;%d;;%s;;%s", quizId, studentId, mapList, submissionTime));
            //formats the graded quiz to written and adds it to the arraylist of strings to written
        }

        return fw.writeFile(path, writableGradedQuizzes);
    }

    public String formatHashmap(HashMap<Integer, Integer> map) { //Used to format the Hashmap to be written
        StringJoiner joiner = new StringJoiner("//");

        for (Integer key : map.keySet()) {
            String question = Integer.toString(key);
            String answer = Integer.toString(map.get(key));
            joiner.add(String.format("%s,,%s", question, answer));
        }
        return joiner.toString();
    }

}
