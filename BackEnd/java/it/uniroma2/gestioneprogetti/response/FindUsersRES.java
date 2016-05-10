package it.uniroma2.gestioneprogetti.response;

import java.util.List;

/**
 * La classe FindUsersRES modella un oggetto Response, contenente i dati degli
 * utenti presenti nel database, che viene trasferito dallo strato Services allo
 * strato Application.
 *
 * @author Luca Talocci, Davide Vitiello
 * @version 1.1 03/05/2016
 */
public class FindUsersRES extends AbstractRES {

    private List<UserRES> usersList;

    public void setUsersList(List<UserRES> usersList) {
        this.usersList = usersList;
    }

    public List<UserRES> getUsersList() {
        return usersList;
    }

}
