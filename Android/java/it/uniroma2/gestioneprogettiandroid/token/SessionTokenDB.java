package it.uniroma2.gestioneprogettiandroid.token;

import android.content.Context;
import android.content.SharedPreferences;

import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

/**
 * Questa classe si occupa di salvare/cancellare e recuperare da un database interno di android
 * il token utilizzato successivamente nelle richieste http.
 * È stato scelto di implementarlo tramite il pattern singleton per avere
 * un'unica istanza globale per tutta l'applicazione.
 * Al metodo getInstance, che si occupa di ritirare o creare l'unica istanza,
 * viene passato il Context di android in modo tale da rendere disponibile
 * lo SharedPreferences.
 */
public class SessionTokenDB implements ISessionTokenDB {

    private static ISessionTokenDB instance;

    private final SharedPreferences sharedPreferences;

    private SessionTokenDB(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static ISessionTokenDB getInstance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SESSION_PREF", Context.MODE_PRIVATE);
        if (instance != null)
            return instance;

        instance = new SessionTokenDB(sharedPreferences);
        return instance;
    }

    /**
     * Restituisce il token presente nel database interno
     *
     * @return il token
     * @throws NullTokenException se non è stato trovato un token
     */
    @Override
    public String getToken() throws NullTokenException {
        String token = sharedPreferences.getString("token", null);

        if(token == null)
            throw new NullTokenException();

        return token;
    }

    /**
     * Inserisce il token nel database interno
     *
     * @param token il token da settare nel db
     */
    @Override
    public void setToken(String token) {
        sharedPreferences.edit().putString("token", token).commit();
    }

    /**
     * Cancella il token dal database interno
     */
    @Override
    public void deleteToken() {
        sharedPreferences.edit().putString("token", null).commit();
    }
}
