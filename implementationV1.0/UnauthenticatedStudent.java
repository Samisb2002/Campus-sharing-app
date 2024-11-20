public class UnauthenticatedStudent extends User {
    public UnauthenticatedStudent(String username) {
        super(username);
    }

    public void requestAccess() {
        System.out.println(username + " needs to authenticate to access services.");
    }
}
