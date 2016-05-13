package it.uniroma2.gestioneprogetti.request;

public class ProjectEmployeesRQS extends AbstractRQS {
    private ProjectRQS project;
    private int[] employees;
    
    public ProjectEmployeesRQS() {
    }
    
    public ProjectEmployeesRQS(ProjectRQS project, int[] employees) {
        this.project = project;
        this.employees = employees;
    }

    public ProjectRQS getProject() {
        return project;
    }

    public void setProject(ProjectRQS project) {
        this.project = project;
    }

    public int[] getEmployees() {
        return employees;
    }

    public void setEmployees(int[] employees) {
        this.employees = employees;
    }  
    
}
