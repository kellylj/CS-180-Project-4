package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.StringJoiner;


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

    }

    @Override
    public void exit() {

    }

    public ArrayList<GradedQuiz> readGradedQuizzes() {
        ArrayList<GradedQuiz> tempGradQuiz = new ArrayList<>();
        String path = "";
        ArrayList<String> contents = fw.readFile(path);

        if (contents == null) {
            return tempGradQuiz;
        }

        for (int i = 0; i < contents.size(); i++) {
            String[] list = contents.get(i).split(";", 4);
            int quizID = Integer.parseInt(list[0]);
            int studentID = Integer.parseInt(list[1]);
            HashMap<Integer, Integer> map = createHashmap(list[2]);
            String submissionTime = list[3];
            tempGradQuiz.add(new GradedQuiz(quizID, studentID, map, submissionTime));
        }
        return tempGradQuiz;
    }

    public HashMap<Integer, Integer> createHashmap(String contents) {
        HashMap<Integer, Integer> map = new HashMap<>();
        String[] list = contents.split("/", -1);

        for (int i = 0; i < list.length; i++) {
            String[] parts = list[i].split(",", 2);
            Integer questionId = Integer.parseInt(parts[0]);
            Integer answerId = Integer.parseInt(parts[1]);
            map.put(questionId, answerId);
        }
        return map;
    }

    public boolean writeGradedQuizzes() {
        ArrayList<String> writableGradedQuizzes = new ArrayList<>();
        String path = "";

        for (int i = 0; i < gradedQuizzes.size(); i++) {
            //int quizId = gradedQuizzes.get(i).getQuizId();
            //int studentId = gradedQuizzes.get(i).getStudentId();
            //String mapList = method for hashmap
            String submissionTime = gradedQuizzes.get(i).getSubmissionTime();
            //writableGradedQuizzes.add(String.format("%d;%d;%s;%s", quizId, studentId, mapList, submissionTime));
        }

        return fw.writeFile(path, writableGradedQuizzes);
    }

    public String formatHashmap(HashMap<Integer, Integer> map) {
        StringJoiner joiner = new StringJoiner("/");

        for (Integer key : map.keySet()) {
            String question = Integer.toString(key);
            String answer = Integer.toString(map.get(key));
            joiner.add(String.format("%s,%s", question, answer));
        }
        return joiner.toString();
    }

}
