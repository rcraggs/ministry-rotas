package rcraggs.rota.forms;

import rcraggs.rota.model.User;

public class UserForm {

    private long id;
    private String username;
    private String password;
    private String email;
    private String forename;
    private String surname;
    private String confirmPassword;
    private boolean newUser;

    public UserForm(){}

    public UserForm(User user){
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        forename = user.getForename();
        surname = user.getSurname();
        newUser = false;

        // TODO add a role into this and the adding form
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }
}
