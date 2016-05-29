package it.uniroma2.gestioneprogettiandroid.tasks;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.activity.LoginActivity;
import it.uniroma2.gestioneprogettiandroid.activity.MainActivity;
import it.uniroma2.gestioneprogettiandroid.adapters.ProjectAdapter;
import it.uniroma2.gestioneprogettiandroid.dao.IProjectDAO;
import it.uniroma2.gestioneprogettiandroid.dao.ISessionDAO;
import it.uniroma2.gestioneprogettiandroid.domain.Project;
import it.uniroma2.gestioneprogettiandroid.exception.InvalidTokenException;
import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;
import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;
import it.uniroma2.gestioneprogettiandroid.tasks.results.ProjectsResult;

/**
 * Created by gaudo on 22/05/16.
 */
public class GetProjectsTask extends AsyncTask<Void, Void, ProjectsResult> {

    private static boolean isRunning = false;

    private final IProjectDAO projectDAO;
    private final ISessionDAO sessionDAO;

    private final Toast toast;
    private final WeakReference<Activity> activityWeakReference;

    private final String INTERNAL_SERVER_ERROR;
    private final String CONNECTION_ERROR;
    private final String ONE_MOMENT;
    private final String SESSION_ERROR;

    public GetProjectsTask(Activity activity) {
        super();

        MainContext mainContext = (MainContext) activity.getApplication();

        this.INTERNAL_SERVER_ERROR = activity.getString(R.string.loginMessage_internalServerError);
        this.CONNECTION_ERROR = activity.getString(R.string.loginMessage_connectionError);
        this.ONE_MOMENT = activity.getString(R.string.loginMessage_oneMoment);
        this.SESSION_ERROR = activity.getString(R.string.loginMessage_sessionError);

        toast = mainContext.getToast();
        sessionDAO = mainContext.getSessionDAO();
        projectDAO = mainContext.getProjectDAO();

        this.activityWeakReference = new WeakReference<>(activity);
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
    protected ProjectsResult doInBackground(Void... params) {
        try {
            String token = sessionDAO.getToken();
            List<Project> projects = projectDAO.getPMProjects(token);

            return new ProjectsResult(projects);
        } catch (NullTokenException | ServiceUnavailableException | InvalidTokenException | IOException e) {
            return new ProjectsResult(e);
        }
    }

    @Override
    protected void onPostExecute(ProjectsResult projects) {
        Activity a = activityWeakReference.get();
        if(a == null) {
            isRunning = false;
            return;
        }

        Exception e = projects.getError();
        if (e == null) {
            ListView listView = (ListView) a.findViewById(R.id.main_listView);
            listView.setAdapter(new ProjectAdapter(a, R.layout.project, projects.getResult()));
        }
        else if(e instanceof NullTokenException || e instanceof InvalidTokenException) {
            toast.setText(SESSION_ERROR);
            toast.show();
            a.startActivity(new Intent(a, LoginActivity.class));
            a.finish();
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

}