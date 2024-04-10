package org.iesvdm.dominio;

public class User {

    public static final String ROLE_ACCESS_MSG = "Se accede a rol";

    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        System.out.println(User.ROLE_ACCESS_MSG);
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
