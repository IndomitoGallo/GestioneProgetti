package it.uniroma2.gestioneprogettiandroid.dao;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

/**
 * Created by gaudo on 22/05/16.
 */
public class SessionDAO implements ISessionDAO {

    private static ISessionDAO instance;

    private final SharedPreferences sharedPreferences;

    private SessionDAO(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static ISessionDAO getInstance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SESSION_PREF", Context.MODE_PRIVATE);
        if (instance != null)
            return instance;

        instance = new SessionDAO(sharedPreferences);
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
