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
		// TODO Auto-generated method stub
		//lms.getUserManager().setUsers();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		//lms.getUserManager().getUsers();
		this.writeUsers();
	}

	public ArrayList<User> readUsers() {
		ArrayList<User> tempUsers = new ArrayList<>();
		String path = "";
		ArrayList<String> contents = fw.readFile(path);

		//TODO: verify the contents of a User, Student, and Teacher

		for (int i = 0; i < contents.size(); i++) {
			String[] list = contents.get(i).split(":", 2); //can combine this line and the first of each if-statement if teacher and student have the same amount of class variables
			if (list[0].equals("teacher")) { //only necessary for constructors if the amount of class variables are the same
				String[] info = list[1].split(";", 3);
				int id = Integer.parseInt(info[0]);
				String username = info[1];
				String password = info[2];
				tempUsers.add(new Teacher(id, username, password));
			} else {
				String[] info = list[1].split(";", 3);
				int id = Integer.parseInt(info[0]);
				String username = info[1];
				String password = info[2];
				tempUsers.add(new Student(id, username, password));
			}
		}
		return tempUsers;
	}

	public boolean writeUsers() {
		ArrayList<String> writableUsers = new ArrayList<>();
		String path = "";
		//TODO: same as in readUsers()
		for (int i = 0; i < users.size(); i++) {
			String write = "";
			if (users.get(i) instanceof Teacher) { //class variables can be written only once outside of the if-statement if teacher and student have the same class variables
				write += "teacher:";
				write += String.format("%d;%s;%s", users.get(i).getID(), users.get(i).getUsername(), users.get(i).getPassword());
			} else {
				write += "student:";
				write += String.format("%d;%s;%s", users.get(i).getID(), users.get(i).getUsername(), users.get(i).getPassword());
			}
			writableUsers.add(write);
		}

		boolean success = fw.writeFile(path, writableUsers);
		return success;
	}

}
