package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.dao.IDAOFactory;
import it.uniroma2.gestioneprogetti.domain.User;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma2.gestioneprogetti.request.UserRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindUserRES;
import it.uniroma2.gestioneprogetti.response.UserRES;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Davide Vitiello
 * @version 1.0
 */
public class UserService implements IUserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final static String LAYERLBL = "****SERVICE LAYER**** ";

    //Qui di seguito viene iniettata la dipendenza della DaoFactory
    @Autowired
    private IDAOFactory daoFactory;

    /*
     * Il metodo insertUser prende l'oggetto RQS proveniente dallo strato "Application", ovvero quello
     * del Controller, copia il contenuto di tale oggetto in un nuovo oggetto User e lancia,
     * tramite la DaoFactory, il metodo insertUser dello strato "Domain". L'esito dell'operazione
     * viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato) all'interno
     * del quale viene settato un messaggio di fallimento o successo.
     * @param request UserRQS richiesta 
     * @param profiles Stringa che rappresenta l'associazione dell'utente ad uno o più profili
     * @return EmptyRES esito
     */
    public EmptyRES insertUser(UserRQS request, String profiles) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio insertUser");
        String message = daoFactory.getUserDao().verifyCreationData(request.getUsername(), request.getName(), profiles);
        EmptyRES response = new EmptyRES();

        if (message.equals("false")) {
            response.setErrorCode("1");
            response.setEsito(false);
        } else {
            User user = new User(request.getId(), request.getUsername(), request.getPassword(), request.getEmail(), request.getName(), request.getSurname(), request.getSkill(), request.getIsDeactivated());
            message = daoFactory.getUserDao().insertUser(user, profiles);
            response = new EmptyRES();
            ;
            if (message.equals("FAIL")) {
                response.setErrorCode("1");
                response.setEsito(false);
            } else {
                response.setErrorCode("0");
                response.setEsito(true);
            }
        }
        response.setMessage(message);
        return response;
    }

    public EmptyRES updateUser(UserRQS request, String profiles) {
        boolean outcome = daoFactory.getUserDao().verifyUpdateData(request.getId(), request.getUsername(), request.getEmail(), profiles);
        EmptyRES response = new EmptyRES();
        if (outcome == false) {
            response.setErrorCode("1");
            response.setMessage("FAIL");
            response.setEsito(false);
        } else {
            User user = new User();
            user.setId(request.getId());
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setSkill(request.getSkill());

            LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio updateUser");

            String message = daoFactory.getUserDao().updateUser(user, profiles);
            response = new EmptyRES();
            response.setMessage(message);
            if (message.equals("FAIL")) {
                response.setErrorCode("1");
                response.setEsito(false);
            } else {
                response.setErrorCode("0");
                response.setEsito(true);
            }
        }
        return response;
    }

    /*
     * Il metodo inizializza una lista di utenti che viene riempita dal 
     * metodo displayUsers dello strato "Domain" e poi viene inizializzato 
     * l'oggetto FindUserRES tramite la lista precedentemente riempita.
     * Infine viene anche settato un messaggio per comunicare l'esito dell'operazione.
     *@return FindUserRES L'oggetto risposta contenente la lista 
     *@author Davide Vitiello
     *
     */
    public FindUserRES displayUsers() {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio displayUsers");
        List<User> usersList = daoFactory.getUserDao().displayUsers();
        FindUserRES response = new FindUserRES();
        if (usersList == null) {
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
        } else {
            response.setMessage("SUCCESS");
            response.setErrorCode("0");
            response.setEsito(true);
        }

        List<UserRES> heroesResList = new ArrayList<>();
        for (User usr : usersList) {
            UserRES heroRES = new UserRES(usr.getId(), usr.getUsername(), usr.getPassword(), usr.getEmail(), usr.getName(), usr.getSurname(), usr.getSkill(), usr.getIsDeactivated());
            heroesResList.add(heroRES);
        }
        response.setUsersList(heroesResList);
        return response;
    }
}
