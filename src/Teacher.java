public class Teacher extends User {
    // make new class permission level?
    private final UserPermission userPermission;
    public Teacher(int ID, String username, String password) {
        super(ID, username, password);
        userPermission = UserPermission.ADMIN;
    }

    public Enum<UserPermission> getUserPermission() {
        return userPermission;
    }

    public String toString() {
        return super.toString() + String.format(", User Permission: %s", userPermission);
    }
}
