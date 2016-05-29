package it.uniroma2.gestioneprogettiandroid.dao;

import java.io.IOException;

import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

/**
 * Created by gaudo on 22/05/16.
 */
public interface ISessionDAO {
    String getToken() throws NullTokenException;

    void setToken(String token);

    void deleteToken();
}
