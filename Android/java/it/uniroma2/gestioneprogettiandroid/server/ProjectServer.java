package it.uniroma2.gestioneprogettiandroid.server;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.domain.Employee;
import it.uniroma2.gestioneprogettiandroid.domain.EmployeeOnProject;
import it.uniroma2.gestioneprogettiandroid.domain.Project;
import it.uniroma2.gestioneprogettiandroid.domain.ProjectDetails;
import it.uniroma2.gestioneprogettiandroid.exception.InvalidTokenException;
import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;

/**
 * Questa classe si occupa di effettuare operazioni di recupero dei dati
 * dei progetti dal server tramite richieste HTTP.
 * È stato scelto di implementarlo tramite il pattern singleton per avere
 * un’unica istanza globale per tutta l’applicazione.
 * Al metodo getInstance, che si occupa di ritirare o creare l’unica istanza, 
 * viene passato il Context di android in modo tale da rendere disponibile
 * alla classe le stringhe presenti nel file resource.
 * Tali stringhe rappresentano delle costanti
 * utilizzate successivamente durante le richieste HTTP.
 */ 
public final class ProjectServer implements IProjectServer {

    private static ProjectServer instance;

    private final String HOST;
    private final int PORT;
    private final String PROJECTS_URL;

    private final String PROJECT_OBJECT;

    private final String PROJECT_ID;
    private final String PROJECT_NAME;
    private final String PROJECT_DESCRIPTION;
    private final String PROJECT_STATUS;
    private final String PROJECT_BUDGET;
    private final String PROJECT_COST;
    private final String PROJECT_PMID;

    private final String EMPLOYEES_ARRAY;

    private final String EMPLOYEE_ID;
    private final String EMPLOYEE_USERNAME;
    private final String EMPLOYEE_PASSWORD;
    private final String EMPLOYEE_EMAIL;
    private final String EMPLOYEE_NAME;
    private final String EMPLOYEE_SURNAME;
    private final String EMPLOYEE_SKILL;
    private final String EMPLOYEE_ISDEACTIVATED;
    private final String EMPLOYEE_SENIORITY;

    private final String PROJECTDETAILS_HOURS_ARRAY;
    private final String PROJECTDETAILS_PMNAME;

    private final String SESSION_ID_PARAM;

    private ProjectServer(Context context) {

        Resources resources = context.getResources();

        String appDirectory = resources.getString(R.string.restserver_appDirectory);

        SESSION_ID_PARAM = resources.getString(R.string.restserver_sessionIdParameter);
        HOST = resources.getString(R.string.restserver_host);
        PORT = resources.getInteger(R.integer.restserver_port);

        PROJECTS_URL = appDirectory + "/" + resources.getString(R.string.restserver_projectsUrl);

        PROJECT_ID = resources.getString(R.string.restserver_project_id);
        PROJECT_NAME = resources.getString(R.string.restserver_project_name);
        PROJECT_DESCRIPTION = resources.getString(R.string.restserver_project_description);
        PROJECT_STATUS = resources.getString(R.string.restserver_project_status);
        PROJECT_BUDGET = resources.getString(R.string.restserver_project_budget);
        PROJECT_COST = resources.getString(R.string.restserver_project_cost);
        PROJECT_PMID = resources.getString(R.string.restserver_project_projectManagerId);

        EMPLOYEES_ARRAY = resources.getString(R.string.restserver_employeesArray);
        EMPLOYEE_ID = resources.getString(R.string.restserver_employee_id);
        EMPLOYEE_USERNAME = resources.getString(R.string.restserver_employee_username);
        EMPLOYEE_PASSWORD = resources.getString(R.string.restserver_employee_password);
        EMPLOYEE_EMAIL = resources.getString(R.string.restserver_employee_email);
        EMPLOYEE_NAME = resources.getString(R.string.restserver_employee_name);
        EMPLOYEE_SURNAME = resources.getString(R.string.restserver_employee_surname);
        EMPLOYEE_SKILL = resources.getString(R.string.restserver_employee_skill);
        EMPLOYEE_ISDEACTIVATED = resources.getString(R.string.restserver_employee_isdeactivated);
        EMPLOYEE_SENIORITY = resources.getString(R.string.restserver_employee_seniority);

        PROJECTDETAILS_HOURS_ARRAY = resources.getString(R.string.restserver_projectdetails_hours);
        PROJECTDETAILS_PMNAME = resources.getString(R.string.restserver_projectdetails_pmName);

        PROJECT_OBJECT = resources.getString(R.string.restserver_project_object);
    }

    public static IProjectServer getInstance(Context context) {
        if (instance != null)
            return instance;

        instance = new ProjectServer(context);

        return instance;
    }

    /**
     * Questo metodo si occupa di recuperare la lista dei progetti dal server
     * tramite una richiesta GET http.
     *
     * @param sessionId il token di sessione
     *
     * @throws IOException se è avvenuto un problema di connessione.
     * @throws InvalidTokenException se il server rifiuta il token passato come parametro nella richiesta http
     * @throws ServiceUnavailableException se il server ha riscontrato un errore interno.
     *
     * @return la lista dei progetti
     */
    @Override
    public List<Project> getPMProjects(String sessionId) throws IOException, InvalidTokenException, ServiceUnavailableException {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        BufferedReader reader = null;
        try {

            StringBuilder result = new StringBuilder();

            URL url = new URL("http", HOST, PORT, String.format("%s?%s=%s", PROJECTS_URL, SESSION_ID_PARAM, sessionId));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");

            int code = connection.getResponseCode();

            if(code == 401)
                throw new InvalidTokenException();

            if(code != 200)
                throw new ServiceUnavailableException();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            try {
                JSONTokener tokener = new JSONTokener(result.toString());
                JSONArray jsonArray = new JSONArray(tokener);

                List<Project> projects = new ArrayList<>();
                for(int i=0, length = jsonArray.length(); i<length; ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = jsonObject.getInt(PROJECT_ID);
                    String name = jsonObject.getString(PROJECT_NAME);
                    String description = jsonObject.getString(PROJECT_DESCRIPTION);
                    String status = jsonObject.getString(PROJECT_STATUS);
                    double budget = jsonObject.getDouble(PROJECT_BUDGET);
                    double cost = jsonObject.getDouble(PROJECT_COST);
                    int pmId = jsonObject.getInt(PROJECT_PMID);

                    projects.add(new Project(id, name, description, status, budget, cost, pmId));
                }
                return projects;

            } catch (JSONException e) {
                throw new RuntimeException();
            }
        } catch(IOException e) {
            throw e;
        } finally {
            if(outputStream != null)
                outputStream.close();
            if(reader != null)
                reader.close();
            if(connection != null)
                connection.disconnect();
        }
    }

    /**
     * Questo metodo si occupa di recuperare i dettagli di un progetto specifico dal server
     * tramite una richiesta GET http.
     *
     * @param id l’id del progetto da recuperare
     * @param sessionId il token di sessione
     *
     * @throws IOException se è avvenuto un problema di connessione.
     * @throws InvalidTokenException se il server rifiuta il token passato come parametro nella richiesta http
     * @throws ServiceUnavailableException se il server ha riscontrato un errore interno.
     *
     * @return i dettagli di un progetto specifico
     */
    @Override
    public ProjectDetails getPMProjectById(int id, String sessionId) throws IOException, ServiceUnavailableException, InvalidTokenException
    {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        BufferedReader reader = null;
        try {

            StringBuilder result = new StringBuilder();

            URL url = new URL("http", HOST, PORT, String.format("%s/%d?%s=%s", PROJECTS_URL, id, SESSION_ID_PARAM, sessionId));

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");

            int code = connection.getResponseCode();

            if(code == 401)
                throw new InvalidTokenException();

            if(code != 200)
                throw new ServiceUnavailableException();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            try {
                // Viene parsato il JSON ricevuto dal server e viene convertito in un JSONObject
                JSONTokener tokener = new JSONTokener(result.toString());
                JSONObject jsonObject = new JSONObject(tokener);
                List<EmployeeOnProject> employeesOnProject = new ArrayList<>();

                JSONArray employeeArray = jsonObject.getJSONArray(EMPLOYEES_ARRAY);
                JSONArray hoursArray = jsonObject.getJSONArray(PROJECTDETAILS_HOURS_ARRAY);

                JSONObject project = jsonObject.getJSONObject(PROJECT_OBJECT);


                Project p = new Project(
                        project.getInt(PROJECT_ID),
                    project.getString(PROJECT_NAME),
                    project.getString(PROJECT_DESCRIPTION),
                    project.getString(PROJECT_STATUS),
                    project.getDouble(PROJECT_BUDGET),
                    project.getDouble(PROJECT_COST),
                    project.getInt(PROJECT_PMID)
                );

                for(int i=0, length = employeeArray.length(); i<length; ++i) {
                    JSONObject jobj = employeeArray.getJSONObject(i);

                    int hour = hoursArray.getInt(i);
                    Employee e = new Employee(
                            jobj.getInt(EMPLOYEE_ID),
                            jobj.getString(EMPLOYEE_USERNAME),
                            jobj.getString(EMPLOYEE_PASSWORD),
                            jobj.getString(EMPLOYEE_EMAIL),
                            jobj.getString(EMPLOYEE_NAME),
                            jobj.getString(EMPLOYEE_SURNAME),
                            jobj.getString(EMPLOYEE_SKILL),
                            jobj.getBoolean(EMPLOYEE_ISDEACTIVATED),
                            jobj.getInt(EMPLOYEE_SENIORITY)
                    );

                    EmployeeOnProject eop = new EmployeeOnProject(e, hour);

                    employeesOnProject.add(eop);
                }

                String pmname = jsonObject.getString(PROJECTDETAILS_PMNAME);

                ProjectDetails projectDetails = new ProjectDetails(p, employeesOnProject, pmname);

                return projectDetails;
            } catch (JSONException e) {
                throw new IOException();
            }
        } catch(IOException e) {
            throw e;
        } finally {
            if(outputStream != null)
                outputStream.close();
            if(reader != null)
                reader.close();
            if(connection != null)
                connection.disconnect();
        }
    }

}
