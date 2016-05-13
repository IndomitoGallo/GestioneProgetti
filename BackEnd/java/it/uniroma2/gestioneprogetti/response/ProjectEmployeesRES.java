package it.uniroma2.gestioneprogetti.response;

public class ProjectEmployeesRES extends AbstractRES {
    private ProjectRES project;
    private int[] employees;
    
    public ProjectEmployeesRES() {
    }
    
    public ProjectEmployeesRES(ProjectRES project, int[] employees) {
        this.project = project;
        this.employees = employees;
    }

    public ProjectRES getProject() {
        return project;
    }

    public void setProject(ProjectRES project) {
        this.project = project;
    }

    public int[] getEmployees() {
        return employees;
    }

    public void setEmployees(int[] employees) {
        this.employees = employees;
    }  
    
}
