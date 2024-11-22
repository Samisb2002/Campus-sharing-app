public class ScoreManager {
    public Integer bananaScore;

    public ScoreManager() {
        this.bananaScore = 5;
    }


    public Integer getScore() {
        return bananaScore;
    }

    public Integer setScore(Integer Score) {
        return this.bananaScore = Score;
    }

    public void IncrementScore() {
        this.bananaScore++;
    }

}