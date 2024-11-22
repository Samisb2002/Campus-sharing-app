public class Profile {

    public AuthenticatedStudent owner;

    public Profile(AuthenticatedStudent owner) {
    this.owner = owner;
    }

    public void displayProfile() {
        System.out.println("Profile of: " + owner.getUserName());
        System.out.println("Email: " + owner.getUserEmail());
        System.out.println("Banana Score: " + owner.scoreManager.getScore());
    }

    public void editProfile(String newName, String newEmail) {
        owner.name = newName;
        owner.email = newEmail;
        System.out.println("Profile updated successfully.");
    }

    public void viewPostedItems() {
        System.out.println("Viewing posted items...");
    }

    public void viewPostedServices() {
        System.out.println("Viewing posted services...");
    }

}