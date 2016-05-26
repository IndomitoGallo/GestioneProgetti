package it.uniroma2.gestioneprogetti.response;

import java.util.List;

public class ProjectEmployeesHoursRES extends AbstractRES {
    private ProjectRES project;
    private List<UserRES> employees;
    private List<Integer> hours;
    private String pmName;

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
