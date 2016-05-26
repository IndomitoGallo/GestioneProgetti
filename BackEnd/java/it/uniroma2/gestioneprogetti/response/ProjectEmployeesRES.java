package it.uniroma2.gestioneprogetti.response;
/**
 * La classe ProjectEmployeesRES modella un oggetto response che incapsula al suo interno 
 * le proprietà ProjectRES "progetto" e int[] "dipendenti" in modo tale
 * da trasportare i dati dallo strato Application allo strato Services.
 * @author Gruppo Talocci
 */
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
