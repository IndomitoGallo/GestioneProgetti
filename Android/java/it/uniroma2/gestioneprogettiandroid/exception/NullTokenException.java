package it.uniroma2.gestioneprogettiandroid.exception;

public class NullTokenException extends Exception {
    public NullTokenException()
    {
        super("Token is null.");
    }
}
