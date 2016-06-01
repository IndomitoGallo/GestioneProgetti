package it.uniroma2.gestioneprogettiandroid.exception;


public class ServiceUnavailableException extends Exception {

    public ServiceUnavailableException()
    {
        super("The service is not available.");
    }

}
