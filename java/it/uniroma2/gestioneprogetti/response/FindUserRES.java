package it.uniroma2.gestioneprogetti.response;

import java.util.List;

/**
 * La classe FindUsersRES modella un oggetto Response, contenente i dati degli 
 * utenti presenti nel database, che viene trasferito dallo strato 
 * Services allo strato Application. 
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class FindUserRES extends AbstractRES {
    
    private List<UserRES> usersList;
    
}
