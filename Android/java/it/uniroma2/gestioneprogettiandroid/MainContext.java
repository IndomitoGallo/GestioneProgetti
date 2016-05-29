package it.uniroma2.gestioneprogettiandroid;

import android.app.Application;
import android.widget.Toast;

import it.uniroma2.gestioneprogettiandroid.dao.IProjectDAO;
import it.uniroma2.gestioneprogettiandroid.dao.ISessionDAO;
import it.uniroma2.gestioneprogettiandroid.dao.IUserDAO;
import it.uniroma2.gestioneprogettiandroid.dao.ProjectDAO;
import it.uniroma2.gestioneprogettiandroid.dao.SessionDAO;
import it.uniroma2.gestioneprogettiandroid.dao.UserDAO;

/**
 * Created by gaudo on 21/05/16.
 */
public final class MainContext extends Application {

    private IProjectDAO projectDAO;

    private IUserDAO userDAO;

    private ISessionDAO sessionDAO;

    private Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();

        if(toast == null)
            toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        if (projectDAO == null) {
            projectDAO = ProjectDAO.getInstance(getApplicationContext());
        }

        if (userDAO == null) {
            userDAO = UserDAO.getInstance(this.getResources());
        }

        if (sessionDAO == null) {
            sessionDAO = SessionDAO.getInstance(this);
        }
    }

    public IProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public IUserDAO getUserDAO() {
        return userDAO;
    }

    public ISessionDAO getSessionDAO() {
        return sessionDAO;
    }

    public Toast getToast() {
        return toast;
    }

}
