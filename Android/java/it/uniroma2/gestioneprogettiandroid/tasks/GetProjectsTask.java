package it.uniroma2.gestioneprogettiandroid.tasks;

import android.app.Activity;
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
import it.uniroma2.gestioneprogettiandroid.adapters.ProjectAdapter;
import it.uniroma2.gestioneprogettiandroid.server.IProjectServer;
import it.uniroma2.gestioneprogettiandroid.token.ISessionTokenDB;
import it.uniroma2.gestioneprogettiandroid.domain.Project;
import it.uniroma2.gestioneprogettiandroid.exception.InvalidTokenException;
import it.uniroma2.gestioneprogettiandroid.exception.NullTokenException;
import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;
import it.uniroma2.gestioneprogettiandroid.tasks.results.ProjectsResult;

/**
 * Questa classe rappresenta il task di recupero dei progetti.
 */
public class GetProjectsTask extends AsyncTask<Void, Void, ProjectsResult> {

    private static boolean isRunning = false;

    private final IProjectServer projectServer;
    private final ISessionTokenDB sessionTokenDB;

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
        sessionTokenDB = mainContext.getSessionTokenDB();
        projectServer = mainContext.getProjectServer();

        this.activityWeakReference = new WeakReference<>(activity);
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
     * Questo metodo effettua su un thread separato le operazioni di recupero dei progetti dal server.
     * In caso di eccezione, viene ritornato un oggetto ProjectsResult con un'eccezione come parametro.
     * 
     * @return il risultato del recupero dei dati
     */
    @Override
    protected ProjectsResult doInBackground(Void... params) {
        try {
            String token = sessionTokenDB.getToken();
            List<Project> projects = projectServer.getPMProjects(token);

            return new ProjectsResult(projects);
        } catch (NullTokenException | ServiceUnavailableException | InvalidTokenException | IOException e) {
            return new ProjectsResult(e);
        }
    }

    /**
     * Questo metodo viene lanciato dopo l'esecuzione del task in background e prende come parametro il valore di ritorno di doInBackground().
     * Se il metodo doInBackground() è andato a buon fine, viene settato l'adapter della lista presente nell'activity con i dati recuperati. Altrimenti viene generato un messaggio di errore.
     * Nel caso in cui ci sia stato un InvalidTokenException o NullTokenException, l'utente viene reindirizzato alla pagina di login.
     * Il parametro in ingresso può essere un oggetto non valido se il processo in background ha avuto un errore.
     * 
     * @param projects il risultato del recupero dei progetti.
     */
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
