package it.uniroma2.gestioneprogetti.bean;

import it.uniroma2.gestioneprogetti.domain.Project;

public class ProjectEmployeesBean {
    private Project project;
    private int[] employees;
    private String sessionId;
    
    public ProjectEmployeesBean() {    
    }
    
    public ProjectEmployeesBean(Project project, int[] employees) {
        this.project = project;
        this.employees = employees;
    }

    public Project getProject() {
        return project;
    }
 
    public void setUser(Project project) {
        this.project = project;
    }

    public int[] getEmployees() {
        return employees;
    }

    public void setEmployees(int[] employees) {
        this.employees = employees;
    }
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }    
}
