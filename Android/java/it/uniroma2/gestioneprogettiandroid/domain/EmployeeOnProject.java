package it.uniroma2.gestioneprogettiandroid.domain;

/**
 * Created by gaudo on 14/05/16.
 */
public class EmployeeOnProject {

    private Employee employee;
    private int hours;

    public EmployeeOnProject(Employee d, int h) {
        this.employee = d;
        this.hours = h;
    }

    public int getHours() {
        return this.hours;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
