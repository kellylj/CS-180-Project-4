/**
 *
 * Super class that contains the details of a particular user
 *
 * @author Sean Lee
 * @version 11/7/21
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

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        String format = "ID: %d, Username: %s, Password: %s";
        return String.format(format, ID, username, password);
    }
}
