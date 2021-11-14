package main;
import java.io.File;
import java.util.ArrayList;

public class UserFileManager implements Manager {

    LearningManagementSystem lms;
    private ArrayList<User> users;
    private FileWrapper fw = new FileWrapper();

    public UserFileManager(LearningManagementSystem lms) {
        this.lms = lms;
        this.users = this.readUsers();
    }

    @Override
    public void init() {
        lms.getUserManager().setUsers(this.users);
    } //Sets the UserManager's arraylist of users after reading saved data

    @Override
    public void exit() {
        this.users = lms.getUserManager().getUsers();
        this.writeUsers();
    } //gets the altered arraylist of users from UserManager after the program finishes, to be written to a file

    public ArrayList<User> readUsers() { //reads the file that stores the user data and constructs an arraylist of users from it
        ArrayList<User> tempUsers = new ArrayList<>();
        String path = "./data/users.txt";
        ArrayList<String> contents = fw.readFile(path);

        if (contents == null) {
            return tempUsers;
        }

        for (int i = 0; i < contents.size(); i++) {
            String[] list = contents.get(i).split("::", 2); //Two "::" colons are used to separate the type of user from the other information
            String[] info = list[1].split(";;", 4); //Two ";;" semicolons are used to separate the information used to construct a user
            int id = Integer.parseInt(info[0]);
            String username = info[1];
            String password = info[2];
            String name = info[3];
            if (list[0].equals("teacher")) {
                tempUsers.add(new Teacher(id, username, password, name));
            } else { //student
                tempUsers.add(new Student(id, username, password, name));
            }
        }
        return tempUsers;
    }

    public boolean writeUsers() { //writes the arraylist of users "users" to a file in order to store the data
        ArrayList<String> writableUsers = new ArrayList<>();
        String path = "./data/users.txt";

        for (int i = 0; i < users.size(); i++) {
            String write = "";
            if (users.get(i) instanceof Teacher) { //differentiate between a teacher and a student
                write += "teacher::";
            } else {
                write += "student::";
            }
            write += String.format("%d;;%s;;%s;;%s", users.get(i).getID(), users.get(i).getUsername(), users.get(i).getPassword(), users.get(i).getName());
            writableUsers.add(write);
        }
        boolean success = fw.writeFile(path, writableUsers);
        return success;
    }

}
