package app.model.user;

public class UserBuilder {
    private Long id;
    private Long money;
    private String name;
    private String email;
    private String pass;
    private String role;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setMoney(Long money) {
        this.money = money;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public UserBuilder setIsAdmin(String role) {
        this.role = role;
        return this;
    }

    public User build() {
        return new User(id, money, name, email, pass, role);
    }
}
