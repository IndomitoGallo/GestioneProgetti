package it.uniroma2.gestioneprogettiandroid.exception;

public class InvalidTokenException extends Exception {

    public InvalidTokenException()
    {
        super("The given token is not valid.");
    }
}
