package it.uniroma2.gestioneprogetti.request;

/**
 * La classe ProjectRQS modella un oggetto Request, contenente i dati di un progetto, 
 * che viene trasferito dallo strato Application allo strato Services. 
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class ProjectRQS extends AbstractRQS {
    
    private int id;
    private String name;
    private String description;
    private String status;
    private double budget;
    private double cost;
    private int projectManager;
       
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
