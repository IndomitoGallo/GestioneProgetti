package it.uniroma2.gestioneprogettiandroid.tasks.params;

/**
 * Questa classe rappresenta i parametri di login utilizzati dal task login.
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
