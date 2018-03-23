package rcraggs.rota.model;

import javax.persistence.*;

@Entity
public class User {

    public enum UserRole {
        ADMIN,
        USER;

        public static final UserRole[] ALL = { ADMIN, USER };
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    private String email;
    private String forename;
    private String surname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(){ }

    public User(String username, String password, UserRole role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() { return role;}
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getRoleAsString() {
        return "ROLE_" + role.name();
    }
}