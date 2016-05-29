package it.uniroma2.gestioneprogettiandroid.tasks.results;

/**
 * Created by gaudo on 22/05/16.
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
