package it.uniroma2.gestioneprogettiandroid;

import android.app.Application;
import android.widget.Toast;

import it.uniroma2.gestioneprogettiandroid.server.IProjectServer;
import it.uniroma2.gestioneprogettiandroid.token.ISessionTokenDB;
import it.uniroma2.gestioneprogettiandroid.server.IUserServer;
import it.uniroma2.gestioneprogettiandroid.server.ProjectServer;
import it.uniroma2.gestioneprogettiandroid.token.SessionTokenDB;
import it.uniroma2.gestioneprogettiandroid.server.UserServer;

/**
 * Questa classe mantiene gli oggetti che devono poter
 * essere accessibili da tutta l’applicazione. Nel nostro caso, i singleton
 * per il recupero dei dati dal server. Il Toast globale viene utilizzato per
 * fare in modo che i messaggi a comparsa vengano sovrascritti
 * e non inseriti in coda (Messaggi più reattivi).
 */ 
public final class MainContext extends Application {

    private IProjectServer projectServer;

    private IUserServer userServer;

    private ISessionTokenDB sessionTokenDB;

    private Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();

        if(toast == null)
            toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        if (projectServer == null) {
            projectServer = ProjectServer.getInstance(getApplicationContext());
        }

        if (userServer == null) {
            userServer = UserServer.getInstance(this.getResources());
        }

        if (sessionTokenDB == null) {
            sessionTokenDB = SessionTokenDB.getInstance(this);
        }
    }

    public IProjectServer getProjectServer() {
        return projectServer;
    }

    public IUserServer getUserServer() {
        return userServer;
    }

    public ISessionTokenDB getSessionTokenDB() {
        return sessionTokenDB;
    }

    public Toast getToast() {
        return toast;
    }

}
