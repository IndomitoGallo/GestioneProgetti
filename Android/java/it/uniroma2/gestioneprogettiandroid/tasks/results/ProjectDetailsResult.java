package it.uniroma2.gestioneprogettiandroid.tasks.results;

import java.util.List;

import it.uniroma2.gestioneprogettiandroid.domain.Project;
import it.uniroma2.gestioneprogettiandroid.domain.ProjectDetails;

/**
 * Created by gaudo on 26/05/16.
 */
public class ProjectDetailsResult {
    private ProjectDetails projectDetails;
    private Exception e;

    public ProjectDetailsResult(Exception e) {
        this.e = e;
    }

    public ProjectDetailsResult(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

    public Exception getError() {
        return e;
    }

    public ProjectDetails getResult() {
        return projectDetails;
    }

}
