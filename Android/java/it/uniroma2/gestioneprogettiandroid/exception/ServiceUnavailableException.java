package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Questa eccezione viene lanciata quando il back-end ha
 * riscontrato un errore interno.
 */ 
public class ServiceUnavailableException extends Exception {

    public ServiceUnavailableException()
    {
        super("The service is not available.");
    }

}
