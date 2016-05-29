package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Created by gaudo on 29/05/16.
 */
public class NullTokenException extends Exception {
    public NullTokenException()
    {
        super("Token is null.");
    }
}
