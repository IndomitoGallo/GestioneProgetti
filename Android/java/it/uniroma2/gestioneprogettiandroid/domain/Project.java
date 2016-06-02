package it.uniroma2.gestioneprogettiandroid.domain;

/**
 * Questa classe fa parte del dominio dell'applicazione e rappresenta
 * un singolo progetto.
 */ 
public class Project {

    private int id;
    private String name;
    private String description;
    private String status;
    private double budget;
    private double cost;
    private int projectManager;

    public Project(
            int id, String name, String description, String status,
            double budget, double cost, int projectManager) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.budget = budget;
        this.cost = cost;
        this.projectManager = projectManager;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }


    public double getBudget() {
        return budget;
    }

    public double getCost() {
        return cost;
    }

    public int getProjectManager() {
        return projectManager;
    }
}
