package it.uniroma2.gestioneprogetti.domain;

/**
 * La classe Project modella la rispettiva entita' del Database e rappresenta
 * quindi una delle classi del dominio dell'applicazione.
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class Project {
    
    private int id;
    private String name;
    private String description;
    private String status;
    private double budget;
    private double cost;
    private int projectManager;
    
    public Project() {
    }
    
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(int projectManager) {
        this.projectManager = projectManager;
    }
    
    
    
}
