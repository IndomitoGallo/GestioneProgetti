package it.uniroma2.gestioneprogettiandroid.server;

import java.io.IOException;

import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;
import it.uniroma2.gestioneprogettiandroid.exception.WrongCredentialsException;

public interface IUserServer {
    String createSession(String username, String password, int profileId) throws IOException, ServiceUnavailableException, WrongCredentialsException;

    void deleteSession(String token) throws IOException;
}
