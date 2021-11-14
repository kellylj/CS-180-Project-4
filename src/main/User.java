package main;
/**
 *
 * Super class that contains the details of a particular user
 *
 * @author Sean Lee
 * @see UserManager
 */
public class User {
    private int ID;
    private String username;
    private String password;
    private String name;

    public User(int ID, String name, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Sets name of user
     * @param name of User
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns name of User
     * @return name of User
     */
    public String getName() {
        return name;
    }

    /**
     * Returns ID of user
     * @return ID of user
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets ID of User
     * @param ID of User
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Returns username of User
     * @return username of User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of User
     * @param username of User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password of User
     * @return password of User
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password of User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * R=
     * @return User in String form
     */
    public String toString() {
        String format = "ID: %d, Username: %s, Password: %s";
        return String.format(format, ID, username, password);
    }
}
