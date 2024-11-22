
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private static UserManager instance;

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void login(String email, String password) {
        for (User user : users) {
            if (user.getUserEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Login successful for: " + user.getUserName());
                return;
            }
        }
        System.out.println("Invalid email or password.");
    }

    public void signup(String name, String email, String password) {
        int newUserId = users.size() + 1;
        User newUser = new User(newUserId, name, email, password);
        users.add(newUser);
        System.out.println("User signed up successfully.");
    }

    public void logout() {
        System.out.println("User logged out successfully.");
    }

    public User getUserById(Integer userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public void incrementBananaScore(AuthenticatedStudent student) {
        student.scoreManager.IncrementScore();
        System.out.println("Banana score incremented for: " + student.getUserName());
    }

    public void decrementBananaScore(AuthenticatedStudent student) {
        int currentScore = student.scoreManager.getScore();
        if (currentScore > 0) {
            student.scoreManager.setScore(currentScore - 1);
            System.out.println("Banana score decremented for: " + student.getUserName());
        } else {
            System.out.println("Banana score cannot go below zero.");
        }
    }

    public Integer getBananaScore(AuthenticatedStudent student) {
        return student.scoreManager.getScore();
    }
}