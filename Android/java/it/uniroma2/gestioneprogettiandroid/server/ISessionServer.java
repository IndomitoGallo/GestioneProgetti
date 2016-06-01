package it.uniroma2.gestioneprogettiandroid.server;

import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

public interface ISessionServer {
    String getToken() throws NullTokenException;

    void setToken(String token);

    void deleteToken();
}
