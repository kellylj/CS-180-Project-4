package main;
/**
 *
 * Class that contains the detail of a student
 *
 * @author Sean Lee
 * @see User
 */
public class Student extends User{
    UserPermission userPermission;
    public Student(int ID, String name, String username, String password) {
        super(ID, name, username, password);
        this.userPermission = UserPermission.USER;
    }

    /**
     * Returns the permission of the student
     * @return Permission of Student
     */
    public Enum<UserPermission> getUserPermission() {
        return userPermission;
    }


    /**
     * Returns the student in string form
     * @return Student in String format
     */
    public String toString() {
        return super.toString() + String.format(", User Permission: %s", userPermission);
    }
}
