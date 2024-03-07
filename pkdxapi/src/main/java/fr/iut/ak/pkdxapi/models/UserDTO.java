package fr.iut.ak.pkdxapi.models;


public class UserDTO {
    private String login;
    private String password;

    public String login() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String password() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
