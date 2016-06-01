package it.uniroma2.gestioneprogettiandroid.exception;

public class WrongCredentialsException extends Exception {
    public WrongCredentialsException()
    {
        super("Wrong credentials given.");
    }
}
