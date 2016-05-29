package it.uniroma2.gestioneprogettiandroid.tasks.params;

/**
 * Created by gaudo on 22/05/16.
 */
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
