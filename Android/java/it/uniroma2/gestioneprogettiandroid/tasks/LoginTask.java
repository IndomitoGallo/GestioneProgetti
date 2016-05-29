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
import it.uniroma2.gestioneprogettiandroid.activity.MainActivity;
import it.uniroma2.gestioneprogettiandroid.dao.ISessionDAO;
import it.uniroma2.gestioneprogettiandroid.dao.IUserDAO;
import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;
import it.uniroma2.gestioneprogettiandroid.exception.WrongCredentialsException;
import it.uniroma2.gestioneprogettiandroid.tasks.params.LoginParams;
import it.uniroma2.gestioneprogettiandroid.tasks.results.LoginResult;

/**
 * Created by gaudo on 22/05/16.
 */
public class LoginTask extends AsyncTask<LoginParams, String, LoginResult> {

    private final IUserDAO userDAO;
    private final ISessionDAO sessionDAO;
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
        userDAO = mainContext.getUserDAO();
        sessionDAO = mainContext.getSessionDAO();

        this.CONNECTING = context.getString(R.string.loginMessage_connecting);
        this.INTERNAL_SERVER_ERROR = context.getString(R.string.loginMessage_internalServerError);
        this.WRONG_CREDENTIALS = context.getString(R.string.loginMessage_wrongCredentials);
        this.CONNECTION_ERROR = context.getString(R.string.loginMessage_connectionError);
        this.ONE_MOMENT = context.getString(R.string.loginMessage_oneMoment);

        this.context = new WeakReference<>(context);
    }

    @Override
    protected LoginResult doInBackground(LoginParams... params) {
        publishProgress(CONNECTING);
        try {
            String sessionId = userDAO.createSession(params[0].username, params[0].password, params[0].profile);
            sessionDAO.setToken(sessionId);

            return new LoginResult(true);

        } catch (ServiceUnavailableException e) {
            return new LoginResult(new Exception(INTERNAL_SERVER_ERROR, e));
        } catch (WrongCredentialsException e) {
            return new LoginResult(new Exception(WRONG_CREDENTIALS, e));
        } catch (IOException e) {
            e.printStackTrace();
            return new LoginResult(new Exception(CONNECTION_ERROR, e));
        }
    }

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

    @Override
    protected void onPostExecute(LoginResult result) {
        Activity c = context.get();

        if(c == null)
            return;

        Exception e = result.getError();

        if (e != null) {
            toast.setText(e.getMessage());
            toast.show();
        }
        else {
            c.startActivity(new Intent(c, MainActivity.class));
            c.finish();
        }
        isRunning = false;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        toast.setText(values[0]);
        toast.show();
    }

}