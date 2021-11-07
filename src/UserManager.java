import java.util.ArrayList;

/**
 *
 * @author Aryan Jain
 * @version 1.0.0
 *
 */

public class UserManager implements Manager {

	private ArrayList<User> users;
	LearningManagementSystem lms;
	
	public UserManager(LearningManagementSystem lms) {
		this.lms = lms;
	}


	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) { users.remove(user); }

	public boolean authenticator(String username, String password) {
		for (User user : users) {
			if ((user.getUsername().equals(username)) && (user.getPassword().equals(password))) {
				return true;
			}
		}
		return false;
	}

	public User getUser(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}



}
