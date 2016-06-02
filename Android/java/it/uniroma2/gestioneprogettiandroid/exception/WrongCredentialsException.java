package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Questa eccezione viene lanciata quando il back-end rifiuta
 * le credenziali inserite in fase di login.
 */ 
public class WrongCredentialsException extends Exception {
    public WrongCredentialsException()
    {
        super("Wrong credentials given.");
    }
}
