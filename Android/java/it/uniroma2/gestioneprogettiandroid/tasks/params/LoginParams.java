package it.uniroma2.gestioneprogettiandroid.tasks.params;

public class LoginParams {
    public final String username;
    public final String password;
    public final int profile;

    public LoginParams(String username, String password, int profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}
