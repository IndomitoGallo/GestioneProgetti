package it.uniroma2.gestioneprogettiandroid.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.activity.LoginActivity;
import it.uniroma2.gestioneprogettiandroid.activity.MainActivity;
import it.uniroma2.gestioneprogettiandroid.dao.ISessionDAO;
import it.uniroma2.gestioneprogettiandroid.dao.IUserDAO;
import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;

/**
 * Created by gaudo on 26/05/16.
 */
public class LogoutTask extends AsyncTask<Void, Void, Exception> {

    private final IUserDAO userDAO;
    private final ISessionDAO sessionDAO;
    private final WeakReference<Activity> context;
    private final Toast toast;

    private static boolean isRunning = false;

    public LogoutTask(Activity context) {
        super();

        MainContext mainContext = (MainContext) context.getApplication();

        toast = mainContext.getToast();
        userDAO = mainContext.getUserDAO();
        sessionDAO = mainContext.getSessionDAO();

        this.context = new WeakReference<>(context);
    }

    @Override
    protected Exception doInBackground(Void... params) {
        String token = null;

        try {
            token = sessionDAO.getToken();
        } catch (NullTokenException e) {
            return null;
        }

        sessionDAO.deleteToken();

        try {
            userDAO.deleteSession(token);
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
