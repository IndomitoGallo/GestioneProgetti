package it.uniroma2.gestioneprogettiandroid.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.activity.LoginActivity;
import it.uniroma2.gestioneprogettiandroid.token.ISessionTokenDB;
import it.uniroma2.gestioneprogettiandroid.server.IUserServer;
import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

/**
 * Questa classe rappresenta il task dell’operazione di logout.
 */
public class LogoutTask extends AsyncTask<Void, Void, Void> {

    private final IUserServer userServer;
    private final ISessionTokenDB sessionTokenDB;
    private final WeakReference<Activity> context;
    private final Toast toast;

    private static boolean isRunning = false;

    public LogoutTask(Activity context) {
        super();

        MainContext mainContext = (MainContext) context.getApplication();

        toast = mainContext.getToast();
        userServer = mainContext.getUserServer();
        sessionTokenDB = mainContext.getSessionTokenDB();

        this.context = new WeakReference<>(context);
    }

    /**
     * Questo metodo effettua su un thread separato l’operazione di logout.
     */
    @Override
    protected Void doInBackground(Void... params) {
        String token = null;

        try {
            token = sessionTokenDB.getToken();
        } catch (NullTokenException e) {
            return null;
        }

        sessionTokenDB.deleteToken();

        try {
            userServer.deleteSession(token);
        } catch (IOException e) {}

        return null;
    }

    /**
     * Questo metodo viene lanciato dopo l’esecuzione del task in background ed avvia la LoginActivity.
     */
    @Override
    protected void onPostExecute(Void v) {
        Activity c = context.get();

        if(c == null)
            return;

        Intent intent = new Intent(c, LoginActivity.class);
        c.startActivity(intent);

        toast.setText("Disconnesso");
        toast.show();

        c.finish();

        isRunning = false;
    }

    /**
     * Questo metodo viene lanciato prima dell’avvio del task e verifica che non ci sia già in esecuzione l’operazione in background.
     * Se lo è, ritorna un messaggio senza avviare il nuovo task.
     */
    @Override
    protected synchronized void onPreExecute() {
        super.onPreExecute();

        if(!isRunning) {
            isRunning = true;
            return;
        }

        cancel(true);
    }
}
