package main;
/**
 *
 * Super class that contains the details of a particular user
 *
 * @author Sean Lee
 * @version 11/14/21
 * @see UserManager
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String name;

    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Sets name of user
     * @param name Name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns name of user
     * @return Name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Returns ID of user
     * @return ID of user
     */
    public int getID() {
        return id;
    }

    /**
     * Sets ID of user
     * @param inputId ID of user
     */
    public void setID(int inputId) {
        this.id = inputId;
    }

    /**
     * Returns username of user
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of user
     * @param username Username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password of user
     * @return Password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password of user
     * @param password Password of user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user in String form
     * @return User in String form
     */
    public String toString() {
        String format = "ID: %d, Username: %s, Password: %s";
        return String.format(format, id, username, password);
    }
}
