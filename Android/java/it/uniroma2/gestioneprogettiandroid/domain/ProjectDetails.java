package it.uniroma2.gestioneprogettiandroid.domain;

import java.util.List;

/**
 * Questa classe fa parte del dominio dell'applicazione e rappresenta
 * i dettagli di un singolo progetto.
 */ 
public class ProjectDetails {
    private final Project project;
    private final List<EmployeeOnProject> employeesOnProject;
    private final String pmName;

    public ProjectDetails(Project p, List<EmployeeOnProject> e, String pmName) {
        this.project = p;
        this.employeesOnProject = e;
        this.pmName = pmName;
    }

    public List<EmployeeOnProject> getEmployeesOnProject() {
        return employeesOnProject;
    }

    public Project getProject() {
        return project;
    }

    public String getPmName() {
        return pmName;
    }

}
