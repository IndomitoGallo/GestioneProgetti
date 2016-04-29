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
    
}
