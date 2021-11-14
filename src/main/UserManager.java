package main;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * The UserManager of the application
 * <p>
 * Implements the Manager class
 * <p>
 * Helps application keep track of all the users
 * and allows application to do several functionalities
 *
 * @author Aryan Jain
 * @version 1.0.0
 *
 */

public class UserManager implements Manager {

	//Arraylist called users that has user objects
	private ArrayList<User> users = new ArrayList<>();

	LearningManagementSystem lms;
	/**
	 * Constructor for the UserManager.
	 * <p>
	 * All initialization is done in {@link #init()}, so the
	 * constructor solely sets the {@link #lms} field.
	 */
	public UserManager(LearningManagementSystem lms) {
		this.lms = lms;
	}

	/**
	 * Returns an ArrayList users of User objects
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * Set an ArrayList users of User objects
	 */
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	/**
	 * Adds a User object to the ArrayList users
	 */
	public void addUser(User user) {
		users.add(user);
	}

	/**
	 * Removes a User object from the ArrayList users
	 */
	public void removeUser(User user) { users.remove(user); }

	/**
	 * Takes an username parameter and a password parameter
	 * and returns boolean if a specific user's
	 * username matches its password
	 */
	public boolean authenticator(String username, String password) {
		for (User user : users) {
			if ((user.getUsername().equals(username)) && (user.getPassword().equals(password))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Iterates through the ArrayList users and returns a user based
	 * on a search by username
	 */
	public User getUser(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Iterates through the ArrayList users and returns a user based
	 * on a search by User ID
	 */
	public User getUserById(int id) {
		for (User user : users) {
			if (user.getID() == id) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Generates a uniqueID for each User object in the ArrayList users
	 */
	public int getUniqueID() {
		Random rand = new Random();
		int id = 0;
		boolean exists = true;
		while (exists) {
			exists = false;
			id = rand.nextInt(999999);
			for (int i = 0; i < users.size(); i++) {
				if(users.get(i).getID() == i) {
					exists = true;
				}
			}
		}
		return id;
	}

	/**
	 * Default init method from the Manager Interface
	 * Is blank because this method is not necessary for this class
	 */
	@Override
	public void init() {

	}

	/**
	 * Default exit method from the Manager Interface
	 * Is blank because this method is not necessary for this class
	 */
	@Override
	public void exit() {

	}



}
