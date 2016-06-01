package it.uniroma2.gestioneprogettiandroid;

import android.app.Application;
import android.widget.Toast;

import it.uniroma2.gestioneprogettiandroid.server.IProjectServer;
import it.uniroma2.gestioneprogettiandroid.server.ISessionServer;
import it.uniroma2.gestioneprogettiandroid.server.IUserServer;
import it.uniroma2.gestioneprogettiandroid.server.ProjectServer;
import it.uniroma2.gestioneprogettiandroid.server.SessionServer;
import it.uniroma2.gestioneprogettiandroid.server.UserServer;

public final class MainContext extends Application {

    private IProjectServer projectServer;

    private IUserServer userServer;

    private ISessionServer sessionServer;

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

        if (sessionServer == null) {
            sessionServer = SessionServer.getInstance(this);
        }
    }

    public IProjectServer getProjectServer() {
        return projectServer;
    }

    public IUserServer getUserServer() {
        return userServer;
    }

    public ISessionServer getSessionServer() {
        return sessionServer;
    }

    public Toast getToast() {
        return toast;
    }

}
