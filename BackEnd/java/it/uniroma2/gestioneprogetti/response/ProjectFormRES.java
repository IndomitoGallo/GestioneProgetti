package it.uniroma2.gestioneprogetti.response;

import java.util.List;

public class ProjectFormRES extends AbstractRES {
    
    private ProjectRES project;
    private List<UserRES> employees;
    private List<UserRES> pms;
    private int[] employeesAssociation;

    public ProjectRES getProject() {
        return project;
    }

    public void setProject(ProjectRES project) {
        this.project = project;
    }

    public List<UserRES> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserRES> employees) {
        this.employees = employees;
    }

    public List<UserRES> getPms() {
        return pms;
    }

    public void setPms(List<UserRES> pms) {
        this.pms = pms;
    }

    public int[] getEmpolyeesAssociation() {
        return employeesAssociation;
    }

    public void setEmpolyeesAssociation(int[] employeesAssociation) {
        this.employeesAssociation = employeesAssociation;
    }
    
    
}
