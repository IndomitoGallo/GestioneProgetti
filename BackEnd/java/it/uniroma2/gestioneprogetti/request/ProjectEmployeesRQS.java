package it.uniroma2.gestioneprogetti.request;
/**
 * La classe ProjectEmployeesRQS modella un oggetto request che incapsula al suo interno 
 * le proprietà ProjectRQS e int[] "array di dipendenti" in modo tale
 * da trasportare i dati dallo strato Application allo strato Services.
 * 
 * @author Gruppo Talocci
 */
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
