public class User {
    protected Integer userId;
    protected String name;
    protected String email;
    protected String password;

    public User(Integer userId, String name, String email,
                String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return name;
    }

    public String getUserEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
