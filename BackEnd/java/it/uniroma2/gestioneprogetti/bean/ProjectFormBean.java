package it.uniroma2.gestioneprogetti.bean;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import java.util.List;

public class ProjectFormBean {
    
    private Project project;
    private List<User> employees;
    private List<User> pms;
    private int[] employeesAssociation;

    public ProjectFormBean() {
    }
    
    public ProjectFormBean(Project project, List<User> employees, List<User> pms, int[] employeesAssociation) {
        this.project = project;
        this.employees = employees;
        this.pms = pms;
        this.employeesAssociation = employeesAssociation;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public List<User> getPms() {
        return pms;
    }

    public void setPms(List<User> pms) {
        this.pms = pms;
    }

    public int[] getEmployeesAssociation() {
        return employeesAssociation;
    }

    public void setEmployeesAssociation(int[] employeesAssociation) {
        this.employeesAssociation = employeesAssociation;
    }    
    
}
