package it.uniroma2.gestioneprogettiandroid.exception;

/**
 * Created by gaudo on 27/05/16.
 */
public class ServiceUnavailableException extends Exception {

    public ServiceUnavailableException()
    {
        super("The service is not available.");
    }

}
