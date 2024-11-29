// Classe abstraite User
public abstract class User {
    // Attributs de la classe User
    protected int userId;   // ID de l'utilisateur, unique
    protected String name;  // Nom de l'utilisateur
    protected String email; // Email de l'utilisateur
    protected String password; // Mot de passe de l'utilisateur

    // Constructeur de la classe User pour initialiser ses attributs
    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Méthode pour récupérer l'ID de l'utilisateur
    public int getUserId() {
        return userId;
    }

    // Méthode pour récupérer le nom de l'utilisateur
    public String getUserName() {
        return name;
    }

    // Méthode pour récupérer l'email de l'utilisateur
    public String getUserEmail() {
        return email;
    }

    // Méthode pour récupérer le mot de passe de l'utilisateur
    public String getPassword() {
        return password;
    }
}
