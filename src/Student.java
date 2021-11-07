/**
 *
 * Class that contains the detail of a student
 *
 * @author Sean Lee
 * @version 11/7/21
 */
public class Student extends User{
    UserPermission userPermission;
    public Student(int ID, String username, String password) {
        super(ID, username, password);
        this.userPermission = UserPermission.USER;
    }

    public String toString() {
        return super.toString() + String.format(", User Permission: %s", userPermission);
    }
}
