package it.uniroma2.gestioneprogetti.response;

import java.util.List;

/**
 * La classe FindProjectsRES modella un oggetto Response, contenente i dati dei 
 * progetti presenti nel database, che viene trasferito dallo strato 
 * Services allo strato Application. 
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class FindProjectsRES extends AbstractRES {
    
     private List<ProjectRES> projectsList;
    
}
