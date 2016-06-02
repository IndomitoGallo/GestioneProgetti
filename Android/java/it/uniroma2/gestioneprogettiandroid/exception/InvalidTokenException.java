package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Questa eccezione viene lanciata quando il back-end rifiuta il token
 * passato come parametro di una richiesta.
 */ 
public class InvalidTokenException extends Exception {

    public InvalidTokenException()
    {
        super("The given token is not valid.");
    }
}
