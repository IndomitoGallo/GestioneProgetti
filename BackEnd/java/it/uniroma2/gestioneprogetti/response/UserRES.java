package it.uniroma2.gestioneprogetti.response;

/**
 * La classe UserRES modella un oggetto Response, contenente i dati di un utente, 
 * che viene trasferito dallo strato Services allo strato Application. 
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class UserRES extends AbstractRES {
    
    private int id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String skill;
    private Boolean isDeactivated;
    
}
