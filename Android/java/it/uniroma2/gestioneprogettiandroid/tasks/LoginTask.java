package it.uniroma2.gestioneprogettiandroid.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.activity.MainActivity;
import it.uniroma2.gestioneprogettiandroid.token.ISessionTokenDB;
import it.uniroma2.gestioneprogettiandroid.server.IUserServer;
import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;
import it.uniroma2.gestioneprogettiandroid.exception.WrongCredentialsException;
import it.uniroma2.gestioneprogettiandroid.tasks.params.LoginParams;
import it.uniroma2.gestioneprogettiandroid.tasks.results.LoginResult;

/**
 * Questa classe rappresenta il task dell'operazione di login.
 */
public class LoginTask extends AsyncTask<LoginParams, String, LoginResult> {

    private final IUserServer userServer;
    private final ISessionTokenDB sessionTokenDB;
    private final WeakReference<Activity> context;
    private final Toast toast;

    private final String INTERNAL_SERVER_ERROR;
    private final String WRONG_CREDENTIALS;
    private final String CONNECTION_ERROR;
    private final String ONE_MOMENT;
    private final String CONNECTING;

    private static boolean isRunning = false;

    public LoginTask(Activity context) {
        super();

        MainContext mainContext = (MainContext) context.getApplicationContext();

        toast = mainContext.getToast();
        userServer = mainContext.getUserServer();
        sessionTokenDB = mainContext.getSessionTokenDB();

        this.CONNECTING = context.getString(R.string.loginMessage_connecting);
        this.INTERNAL_SERVER_ERROR = context.getString(R.string.loginMessage_internalServerError);
        this.WRONG_CREDENTIALS = context.getString(R.string.loginMessage_wrongCredentials);
        this.CONNECTION_ERROR = context.getString(R.string.loginMessage_connectionError);
        this.ONE_MOMENT = context.getString(R.string.loginMessage_oneMoment);

        this.context = new WeakReference<>(context);
    }

    /**
     * Questo metodo effettua su un thread separato l'operazione di login.
     * In caso di eccezione, viene ritornato un oggetto LoginResult con un'eccezione come parametro.
     * 
     * @param params i parametri di login
     *
     * @return il risultato del login
     */
    @Override
    protected LoginResult doInBackground(LoginParams... params) {
        publishProgress(CONNECTING);
        try {
            String sessionId = userServer.createSession(params[0].username, params[0].password, params[0].profile);
            sessionTokenDB.setToken(sessionId);

            return new LoginResult(true);

        } catch (ServiceUnavailableException | WrongCredentialsException | IOException e) {
            return new LoginResult(e);
        }
    }

    /**
     * Questo metodo viene lanciato prima dell'avvio del task e verifica che non ci sia già in esecuzione l'operazione in background.
     * Se lo è, ritorna un messaggio senza avviare il nuovo task.
     */
    @Override
    protected synchronized void onPreExecute() {
        super.onPreExecute();

        if(!isRunning) {
            isRunning = true;
            return;
        }

        toast.setText(ONE_MOMENT);
        toast.show();

        cancel(true);
    }

    /**
     * Questo metodo viene lanciato dopo l'esecuzione del task in background e prende come parametro il valore di ritorno di doInBackground().
     * Se il metodo doInBackground() è andato a buon fine, viene avviata la MainActivity. Altrimenti viene generato un messaggio di errore.
     * Il parametro in ingresso può essere un oggetto non valido se il processo in background ha avuto un errore.
     * 
     * @param result il risultato del login.
     */
    @Override
    protected void onPostExecute(LoginResult result) {
        Activity c = context.get();

        if(c == null)
            return;

        Exception e = result.getError();

        if (e == null) {
            c.startActivity(new Intent(c, MainActivity.class));
            c.finish();
        }
        else if(e instanceof WrongCredentialsException) {
            toast.setText(WRONG_CREDENTIALS);
            toast.show();
        }
        else if(e instanceof ServiceUnavailableException) {
            toast.setText(INTERNAL_SERVER_ERROR);
            toast.show();
        }
        else {
            toast.setText(CONNECTION_ERROR);
            toast.show();
        }

        isRunning = false;
    }

    /**
     * Questo metodo viene lanciato ogni volta che viene richiamato il metodo publishProgress() nel metodo doInBackground()
     *
     * @param values la stringa da visualizzare
     */
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        toast.setText(values[0]);
        toast.show();
    }

}
