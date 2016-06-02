package it.uniroma2.gestioneprogettiandroid.tasks.results;

import java.util.List;

import it.uniroma2.gestioneprogettiandroid.domain.Project;

/**
 * Questa classe rappresenta il risultato del recupero dei progetti
 */
public class ProjectsResult {
    private List<Project> projects;
    private Exception e;

    public ProjectsResult(Exception e) {
        this.e = e;
    }

    public ProjectsResult(List<Project> projects) {
        this.projects = projects;
    }

    public Exception getError() {
        return e;
    }

    public List<Project> getResult() {
        return projects;
    }
}
