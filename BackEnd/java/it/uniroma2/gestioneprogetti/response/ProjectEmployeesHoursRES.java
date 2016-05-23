package it.uniroma2.gestioneprogetti.response;

import java.util.List;

public class ProjectEmployeesHoursRES extends AbstractRES {
    private ProjectRES project;
    private List<Integer> employees;
    private List<Integer> hours;
    private String pmName;

    public ProjectRES getProject() {
        return project;
    }

    public void setProject(ProjectRES project) {
        this.project = project;
    }

    public List<Integer> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Integer> employees) {
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
