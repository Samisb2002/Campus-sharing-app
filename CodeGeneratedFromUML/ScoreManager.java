public class ScoreManager {
    private Integer bananaScore;

    public ScoreManager() {
        this.bananaScore = 5;
    }

    public Integer getScore() {
        return bananaScore;
    }

    public void setScore(Integer score) {
        this.bananaScore = score;
    }

    public void incrementScore() {
        this.bananaScore++;
    }

    public void decrementScore() {
        if (this.bananaScore > 0) {
            this.bananaScore--;
        }
    }
}
