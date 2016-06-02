package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Questa eccezione viene lanciata quando si cerca di recuperare
 * un token inesistente (o non più esistente) dal dispositivo android.
 */ 
public class NullTokenException extends Exception {
    public NullTokenException()
    {
        super("Token is null.");
    }
}
