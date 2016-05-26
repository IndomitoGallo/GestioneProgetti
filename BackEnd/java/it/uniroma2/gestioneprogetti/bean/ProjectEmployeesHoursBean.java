package it.uniroma2.gestioneprogetti.bean;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import java.util.List;

public class ProjectEmployeesHoursBean {
    private Project project;
    private List<User> employees;
    private List<Integer> hours;
    private String pmName;
    
    public ProjectEmployeesHoursBean() {
    }

    public ProjectEmployeesHoursBean(Project project, List<User> employees, List<Integer> hours, String pmName) {
        this.project = project;
        this.employees = employees;
        this.hours = hours;
        this.pmName = pmName;
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

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
    
    
}
