package it.uniroma2.gestioneprogetti.response;

/**
 * La classe ProjectRES modella un oggetto Response, contenente i dati di un progetto, 
 * che viene trasferito dallo strato Services allo strato Application. 
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class ProjectRES extends AbstractRES {
    
    private int id;
    private String name;
    private String description;
    private String status;
    private double budget;
    private double cost;
    private int projectManager;

    public ProjectRES(int id, String name, String description, String status, double budget, double cost, int projectManager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.budget = budget;
        this.cost = cost;
        this.projectManager = projectManager;
    }
    
    
}
