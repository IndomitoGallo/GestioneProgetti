package it.uniroma2.gestioneprogettiandroid.domain;

/**
 * Questa classe fa parte del dominio dell'applicazione e rappresenta
 * l'associazione dipendente <-> ore lavorate su un progetto.
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
