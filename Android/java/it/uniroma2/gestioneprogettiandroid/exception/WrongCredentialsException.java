package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Created by gaudo on 27/05/16.
 */
public class WrongCredentialsException extends Exception {
    public WrongCredentialsException()
    {
        super("Wrong credentials given.");
    }
}
