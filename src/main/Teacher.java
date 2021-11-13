package main;
/**
 *
 * Class that contains the detail of a teacher
 *
 * @author Sean Lee
 * @version 11/7/21
 * @see User
 */
public class Teacher extends User {
    private final UserPermission userPermission;
    public Teacher(int ID, String name, String username, String password) {
        super(ID, name, username, password);
        userPermission = UserPermission.ADMIN;
    }

    public Enum<UserPermission> getUserPermission() {
        return userPermission;
    }


    public String toString() {
        return super.toString() + String.format(", User Permission: %s", userPermission);
    }
}
