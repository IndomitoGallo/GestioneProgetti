package it.uniroma2.gestioneprogettiandroid.server;

import android.content.Context;
import android.content.SharedPreferences;

import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

public class SessionServer implements ISessionServer {

    private static ISessionServer instance;

    private final SharedPreferences sharedPreferences;

    private SessionServer(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static ISessionServer getInstance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SESSION_PREF", Context.MODE_PRIVATE);
        if (instance != null)
            return instance;

        instance = new SessionServer(sharedPreferences);
        return instance;
    }

    @Override
    public String getToken() throws NullTokenException {
        String token = sharedPreferences.getString("token", null);

        if(token == null)
            throw new NullTokenException();

        return token;
    }

    @Override
    public void setToken(String token) {
        sharedPreferences.edit().putString("token", token).commit();
    }

    @Override
    public void deleteToken() {
        sharedPreferences.edit().putString("token", null).commit();
    }
}
