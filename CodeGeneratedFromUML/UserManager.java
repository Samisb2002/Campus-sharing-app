import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private static UserManager instance;

    private String usersCSVFile = "users.csv";

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void loadUsersFromCSV(String filename) {
        this.usersCSVFile = filename; 
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (firstLine) {
                    firstLine = false; 
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length >= 5) {
                    int userId = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    String email = fields[2].trim();
                    String password = fields[3].trim();
                    int bananaScore = Integer.parseInt(fields[4].trim());
                    AuthenticatedStudent user = new AuthenticatedStudent(
                        userId, name, email, password);
                    user.scoreManager.setScore(bananaScore);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users CSV: " + e.getMessage());
        }
    }

    public AuthenticatedStudent login(String email, String password) {
        for (User user : users) {
            if (user.getUserEmail().equals(email.trim()) &&
                user.getPassword().equals(password.trim())) {
                if (user instanceof AuthenticatedStudent) {
                    System.out.println("Login successful for: " +
                                       user.getUserName());
                    return (AuthenticatedStudent) user;
                }
                System.out.println("Only authenticated students can log in.");
                return null;
            }
        }
        System.out.println("Invalid email or password.");
        return null;
    }

    public void signup(String name, String email, String password) {
        int newUserId = generateNewUserId();
        AuthenticatedStudent newUser = new AuthenticatedStudent(
            newUserId, name, email, password);
        users.add(newUser);
        CSVUtils.appendUserToCSV(usersCSVFile, newUser);
        System.out.println("User signed up successfully as an " +
                           "AuthenticatedStudent.");
    }

    private int generateNewUserId() {
        int maxId = 0;
        for (User user : users) {
            if (user.getUserId() > maxId) {
                maxId = user.getUserId();
            }
        }
        return maxId + 1;
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
        return null;
    }

    public void saveUsersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(usersCSVFile))) {
            // Write header
            bw.write("userId,name,email,password,bananaScore");

            // Write user data
            for (User user : users) {
                if (user instanceof AuthenticatedStudent) {
                    AuthenticatedStudent student = (AuthenticatedStudent) user;
                    bw.newLine();
                    StringBuilder sb = new StringBuilder();
                    sb.append(student.getUserId()).append(",");
                    sb.append(student.getUserName()).append(",");
                    sb.append(student.getUserEmail()).append(",");
                    sb.append(student.getPassword()).append(",");
                    sb.append(student.scoreManager.getScore());
                    bw.write(sb.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving users to CSV: " +
                               e.getMessage());
        }
    }
}
