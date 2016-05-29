package it.uniroma2.gestioneprogettiandroid.dao;

import java.io.IOException;

import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;
import it.uniroma2.gestioneprogettiandroid.exception.WrongCredentialsException;

/**
 * Created by gaudo on 22/05/16.
 */
public interface IUserDAO {
    String createSession(String username, String password, int profileId) throws IOException, ServiceUnavailableException, WrongCredentialsException;

    void deleteSession(String token) throws IOException;
}
