package main;

import java.util.ArrayList;
import java.util.HashMap;

public class GradedQuizFileManager implements Manager {

    LearningManagementSystem lms;
    private ArrayList<GradedQuiz> gradedQuizzes;
    private FileWrapper fw = new FileWrapper();

    public GradedQuizFileManager(LearningManagementSystem lms) {
       this.lms = lms;
       //this.gradedQuizzes = this.readGradedQuizzes();
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
            String submissionTime = list[0];
            int quizID = Integer.parseInt(list[1]);
            int studentID = Integer.parseInt(list[2]);
            HashMap<String, String> map = createHashmap(list[3]);
            //tempGradQuiz.add(new GradedQuiz());
        } //need a second constructor for GradedQuiz so that I can fill in all the values, go over HashMap values
        return tempGradQuiz;
    }

    public HashMap<String, String> createHashmap(String contents) {
        HashMap<String, String> map = new HashMap<>();
        String[] list = contents.split("/", -1);

        for (int i = 0; i < list.length; i++) {
            String[] parts = list[i].split(",", 2);
        }

        return map;
    }


}
