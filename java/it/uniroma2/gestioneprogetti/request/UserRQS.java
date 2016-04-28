package it.uniroma2.gestioneprogetti.request;

/**
 * La classe UserRQS modella un oggetto Request, contenente i dati di un utente, 
 * che viene trasferito dallo strato Application allo strato Services. 
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class UserRQS extends AbstractRQS {
    
    private int id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String skill;
    private Boolean isDeactivated;
    
}
