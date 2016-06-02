package it.uniroma2.gestioneprogettiandroid.tasks.results;

/**
 * Questa classe rappresenta il risultato del login
 */
public class LoginResult {
    private boolean result = false;
    private Exception e;

    public LoginResult(Exception e) {
        this.e = e;
    }

    public LoginResult(boolean b) {
        this.result = b;
    }

    public Exception getError() {
        return e;
    }

    public boolean getResult() {
        return result;
    }
}
