package it.uniroma2.gestioneprogettiandroid.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.activity.LoginActivity;
import it.uniroma2.gestioneprogettiandroid.server.ISessionServer;
import it.uniroma2.gestioneprogettiandroid.server.IUserServer;
import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

public class LogoutTask extends AsyncTask<Void, Void, Exception> {

    private final IUserServer userServer;
    private final ISessionServer sessionServer;
    private final WeakReference<Activity> context;
    private final Toast toast;

    private static boolean isRunning = false;

    public LogoutTask(Activity context) {
        super();

        MainContext mainContext = (MainContext) context.getApplication();

        toast = mainContext.getToast();
        userServer = mainContext.getUserServer();
        sessionServer = mainContext.getSessionServer();

        this.context = new WeakReference<>(context);
    }

    @Override
    protected Exception doInBackground(Void... params) {
        String token = null;

        try {
            token = sessionServer.getToken();
        } catch (NullTokenException e) {
            return null;
        }

        sessionServer.deleteToken();

        try {
            userServer.deleteSession(token);
        } catch (IOException e) {}

        return null;
    }

    @Override
    protected void onPostExecute(Exception e) {
        Activity c = context.get();

        if(c == null)
            return;

        if (e != null) {
            toast.setText(e.getMessage());
            toast.show();

            return;
        }

        Intent intent = new Intent(c, LoginActivity.class);
        c.startActivity(intent);

        toast.setText("Disconnesso");
        toast.show();

        c.finish();

        isRunning = false;
    }

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
