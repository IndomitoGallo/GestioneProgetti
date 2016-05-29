package it.uniroma2.gestioneprogettiandroid.tasks;

import android.content.Context;
import android.os.AsyncTask;

import it.uniroma2.gestioneprogettiandroid.dao.IProjectDAO;
import it.uniroma2.gestioneprogettiandroid.dao.ISessionDAO;
import it.uniroma2.gestioneprogettiandroid.tasks.results.ProjectDetailsResult;

/**
 * Created by gaudo on 26/05/16.
 */
public class ShowProjectTask extends AsyncTask<Void, Void, ProjectDetailsResult> {

    private final IProjectDAO projectDAO;
    private final ISessionDAO sessionDAO;
    private final Context context;

    public ShowProjectTask(Context context, IProjectDAO projectDAO, ISessionDAO sessionDAO) {
        super();

        this.context = context;
        this.projectDAO = projectDAO;
        this.sessionDAO = sessionDAO;
    }

    @Override
    protected ProjectDetailsResult doInBackground(Void... params) {
        return null;
    }
}
