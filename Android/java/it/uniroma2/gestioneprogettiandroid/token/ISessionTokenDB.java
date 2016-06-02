package it.uniroma2.gestioneprogettiandroid.token;

import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

public interface ISessionTokenDB {
    String getToken() throws NullTokenException;

    void setToken(String token);

    void deleteToken();
}
