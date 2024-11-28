// Classe UserManager
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    // Liste des utilisateurs enregistrés
    private List<User> users = new ArrayList<>();
    // Instance unique de UserManager (singleton)
    private static UserManager instance;

    // Fichier CSV pour stocker les utilisateurs
    private String usersCSVFile = "users.csv";

    // Constructeur privé pour empêcher la création d'instances directement
    private UserManager() {}

    // Méthode pour récupérer l'instance unique de UserManager (singleton)
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Méthode pour récupérer la liste des utilisateurs
    public List<User> getUsers() {
        return users;
    }

    // Méthode pour charger les utilisateurs depuis un fichier CSV
    public void loadUsersFromCSV(String filename) {
        this.usersCSVFile = filename;  // Spécifie le fichier à charger
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true; // Ignorer la première ligne d'entête
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignore les lignes vides
                }
                if (firstLine) {
                    firstLine = false; // Ignore la première ligne (entête)
                    continue;
                }
                // Sépare les champs par des virgules
                String[] fields = line.split(",");
                if (fields.length >= 5) {
                    // Récupère les informations de l'utilisateur depuis les champs
                    int userId = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    String email = fields[2].trim();
                    String password = fields[3].trim();
                    int bananaScore = Integer.parseInt(fields[4].trim());

                    // Crée un utilisateur AuthenticatedStudent et ajoute à la liste
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

    // Méthode pour effectuer une connexion avec l'email et le mot de passe
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

    // Méthode pour inscrire un nouvel utilisateur
    public void signup(String name, String email, String password) {
        int newUserId = generateNewUserId();  // Génère un nouvel ID utilisateur
        AuthenticatedStudent newUser = new AuthenticatedStudent(
            newUserId, name, email, password);
        users.add(newUser);  // Ajoute l'utilisateur à la liste
        CSVUtils.appendUserToCSV(usersCSVFile, newUser);  // Sauvegarde dans le fichier CSV
        System.out.println("User signed up successfully as an AuthenticatedStudent.");
    }

    // Méthode pour générer un nouvel ID utilisateur
    private int generateNewUserId() {
        int maxId = 0;
        for (User user : users) {
            if (user.getUserId() > maxId) {
                maxId = user.getUserId();
            }
        }
        return maxId + 1;
    }

    // Méthode pour déconnecter l'utilisateur
    public void logout() {
        System.out.println("User logged out successfully.");
    }

    // Méthode pour récupérer un utilisateur par son ID
    public User getUserById(Integer userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    // Méthode pour sauvegarder tous les utilisateurs dans un fichier CSV
    public void saveUsersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(usersCSVFile))) {
            // Écrit l'entête dans le fichier CSV
            bw.write("userId,name,email,password,bananaScore");
            // Écrit chaque utilisateur dans le fichier
            for (User user : users) {
                if (user instanceof AuthenticatedStudent) {
                    AuthenticatedStudent student = (AuthenticatedStudent) user;
                    bw.newLine();
                    bw.write(student.getUserId() + "," + student.getUserName() + "," +
                             student.getUserEmail() + "," + student.getPassword() + "," +
                             student.scoreManager.getScore());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving users to CSV: " + e.getMessage());
        }
    }
}
