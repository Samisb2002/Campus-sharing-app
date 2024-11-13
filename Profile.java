public class Profile {
    private User user;

    public Profile(User user) {
        this.user = user;
    }

    public void showProfile() {
        System.out.println("User: " + user.getUsername() + ", Bananas: " + user.getBananas());
    }
}
