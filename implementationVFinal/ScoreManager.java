public class ScoreManager {
    private Integer bananaScore;  // Variable d'instance pour stocker le score

    // Constructeur par défaut
    public ScoreManager() {
        this.bananaScore = 5;  // Initialisation du score à 5
    }

    // Méthode pour obtenir le score
    public Integer getScore() {
        return bananaScore;
    }

    // Méthode pour définir le score
    public void setScore(Integer score) {
        this.bananaScore = score;
    }

    // Méthode pour incrémenter le score de 1
    public void incrementScore() {
        this.bananaScore++;
    }

    // Méthode pour décrémenter le score de 1 (ne descend pas en dessous de 0)
    public void decrementScore() {
        if (this.bananaScore > 0) {
            this.bananaScore--;
        }
    }
}
