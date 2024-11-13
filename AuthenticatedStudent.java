public class AuthenticatedStudent extends User {
    public AuthenticatedStudent(String username) {
        super(username);
    }

    public void accessService() {
        System.out.println(username + " can access services.");
    }
}
