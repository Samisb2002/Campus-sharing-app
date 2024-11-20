public abstract class User {
    protected String username;
    protected int bananas = 5;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getBananas() {
        return bananas;
    }

    public void addBanana() {
        bananas += 1;
    }

    public boolean useBanana() {
        if (bananas > 0) {
            bananas -= 1;
            return true;
        }
        return false;
    }
}
