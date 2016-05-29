package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Created by gaudo on 27/05/16.
 */
public class InvalidTokenException extends Exception {

    public InvalidTokenException()
    {
        super("The given token is not valid.");
    }
}
