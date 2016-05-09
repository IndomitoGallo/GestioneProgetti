package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.dao.IDAOFactory;
import it.uniroma2.gestioneprogetti.domain.User;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.UserProfilesRQS;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma2.gestioneprogetti.request.UserRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindUserRES;
import it.uniroma2.gestioneprogetti.response.UserProfilesRES;
import it.uniroma2.gestioneprogetti.response.UserRES;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/*
*@author Davide Vitiello
*/

@Service("userService")
public class UserService implements IUserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final static String LAYERLBL = "****SERVICE LAYER**** ";

    //Qui di seguito viene iniettata la dipendenza della DaoFactory
    @Autowired
    private IDAOFactory daoFactory;

    /**
     * Il metodo insertUser prende l'oggetto RQS proveniente dallo strato "Application", ovvero quello
     * del Controller, esso contiene un oggetto UserRQS con i dati dell'utente e un array di interi con
     * gli id dei profili associati ad esso.
     * Questo metodo sfrutta i metodi forniti dallo strato "Domain" per effettuare la verifica dei dati
     * inseriti e per inserire l'utente nel DB. Inoltre inserisce anche le associazioni con i profili.
     * L'esito dell'operazione viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato)
     * all'interno del quale viene settato un messaggio di fallimento o successo.
     * @param request UserProfilesRQS oggetto che incapsula i dati della richiesta 
     * @return EmptyRES oggetto che incapsula l'esito del servizio
     * @author Davide Vitiello, Lorenzo Bernabei
     */
    @Override
    public EmptyRES insertUser(UserProfilesRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio insertUser");
        UserRQS userRequest = request.getUser();
        int[] profiles = request.getProfiles();
        EmptyRES response = new EmptyRES();
        
        String outcome = daoFactory.getUserDao().verifyCreationData(userRequest.getUsername(),
                                                                    userRequest.getEmail());        

        if (outcome.equals("false")) {
            response.setMessage(outcome);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        if (outcome.equals("FAIL")) {
            response.setMessage(outcome);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
         
        User user = new User(userRequest.getId(), userRequest.getUsername(), userRequest.getPassword(),
                             userRequest.getEmail(), userRequest.getName(), userRequest.getSurname(),
                             userRequest.getSkill(), userRequest.getIsDeactivated());
        String result = daoFactory.getUserDao().insertUser(user);
        
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        result = daoFactory.getUserDao().insertProfilesAssociation(user.getId(), profiles);
        
        if (result.equals("FAIL")) {
            /*Se viene inserito con successo l'utente ma non i profili
            bisogna abortire l'intera procedura. Si elimina l'utente inserito
            con successo precedentemente, il che si traduce nell'eliminazione
            del record ma anche dei profili associati e inseriti fino a quel
            momento (prima dell'eccezione).*/
            daoFactory.getUserDao().deleteUser(user);
            
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        response.setMessage(result);
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }

    /**
     * Il metodo updateUser prende l'oggetto RQS proveniente dallo strato "Application", ovvero quello
     * del Controller, esso contiene un oggetto UserRQS con i nuovi dati dell'utente e un array di interi con
     * gli id dei nuovi profili associati ad esso.
     * Questo metodo sfrutta i metodi forniti dallo strato "Domain" per effettuare la verifica dei nuovi dati
     * inseriti e per aggiornare i dati dell'utente nel DB. Inoltre aggiorna anche le associazioni con i profili.
     * L'esito dell'operazione viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato)
     * all'interno del quale viene settato un messaggio di fallimento o successo.
     * @param request UserProfilesRQS oggetto che incapsula i dati della richiesta 
     * @return EmptyRES oggetto che incapsula l'esito del servizio
     * @author Davide Vitiello, Lorenzo Bernabei
     */
    @Override
    public EmptyRES updateUser(UserProfilesRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio updateUser");
        UserRQS userRequest = request.getUser();
        int[] profiles = request.getProfiles();
        EmptyRES response = new EmptyRES();
        
        String outcome = daoFactory.getUserDao().verifyUpdateData(userRequest.getId(),
                                                                  userRequest.getUsername(),
                                                                  userRequest.getEmail());

        if (outcome.equals("false")) {
            response.setMessage(outcome);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        if (outcome.equals("FAIL")) {
            response.setMessage(outcome);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
         
        User user = new User(userRequest.getId(), userRequest.getUsername(), userRequest.getPassword(),
                             userRequest.getEmail(), userRequest.getName(), userRequest.getSurname(),
                             userRequest.getSkill(), userRequest.getIsDeactivated());
        String result = daoFactory.getUserDao().updateUser(user);
        
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        //result = daoFactory.getUserDao().updateProfilesAssociation(user.getId(), profiles);
        
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        response.setMessage(result);
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }

    /**
     * Il metodo retrieveUser prende un oggetto UserRQS proveniente dallo strato "Application", ovvero quello
     * del Controller, contenente solo l'id dell'utente da visualizzare.
     * Questo metodo sfrutta i metodi forniti dallo strato "Domain" per effettuare la retrieve dell'utente e
     * la retrieve dell'associazione con i profili.
     * L'esito dell'operazione viene memorizzato in un oggetto UserProfilesRES all'interno del quale viene settato
     * un messaggio di fallimento o successo.
     * @param request UserRQS oggetto che incapsula i dati della richiesta 
     * @return UserProfilesRES oggetto che incapsula i dati dell'utente, le associazioni con i profili e
     * l'esito del servizio
     * @author Lorenzo Bernabei
     */
    @Override
    public UserProfilesRES retrieveUser(UserRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio retrieveUser");
        UserProfilesRES response = new UserProfilesRES();
                
        User user = new User(request.getId(), request.getUsername(), request.getPassword(),
                             request.getEmail(), request.getName(), request.getSurname(),
                             request.getSkill(), request.getIsDeactivated());
        String result = daoFactory.getUserDao().retrieveUser(user);
        
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        int[] profiles = null;//daoFactory.getUserDao().retrieveProfilesAssociation(user.getId(), profiles);
        
        if (profiles == null) {
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        UserRES userResponse = new UserRES(user.getId(), user.getUsername(), user.getPassword(),
                                           user.getEmail(), user.getName(), user.getSurname(),
                                           user.getSkill(), user.getIsDeactivated());
        response.setUser(userResponse);
        response.setProfiles(profiles);
        
        response.setMessage(result);
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }
    
    /**
     * Il metodo displayUsers(EmptyRQS request) inizializza una lista di utenti
     * che viene riempita dal metodo displayUsers dello strato "Domain" e poi
     * viene inizializzato l'oggetto FindUserRES tramite la lista
     * precedentemente riempita. Infine viene anche settato un messaggio per
     * comunicare l'esito dell'operazione.
     *
     * @param request EmptyRQS oggetto di richiesta "vuoto"
     * @return FindUserRES oggetto che incapsula la lista di usenti e l'esito
     * del servizio
     * @author Davide Vitiello
     */
    @Override
    public FindUserRES displayUsers(EmptyRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio displayUsers");
        List<User> usersList = daoFactory.getUserDao().displayUsers();
        FindUserRES response = new FindUserRES();

        if (usersList == null) {
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }

        List<UserRES> heroesResList = new ArrayList<>();
        for (User usr : usersList) {
            UserRES heroRES = new UserRES(usr.getId(), usr.getUsername(), usr.getPassword(), usr.getEmail(),
                    usr.getName(), usr.getSurname(), usr.getSkill(), usr.getIsDeactivated());
            heroesResList.add(heroRES);
        }
        response.setUsersList(heroesResList);

        response.setMessage("SUCCESS");
        response.setErrorCode("0");
        response.setEsito(true);

        return response;
    }

    /**
     * Il metodo verifyLoginData non fa altro che chiamare il metodo del livello
     * sottostante di UserDAO per verificare che i dati di login di un utente
     * contenuti nella request rispettino i vincoli necessari. Viene restituita
     * una response contenente un messaggio di errore nel caso in cui i vincoli
     * non sono stati rispettati. Altrimenti se i vincoli sono stati soddisfatti
     * viene restituita una response contenente un messaggio di avvenuto
     * successo.
     * @param request UserRQS
     * @return EmptyRES response
     * @author L.Camerlengo
     */
    @Override
    public EmptyRES verifyLoginData(UserRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio verifyLoginData");
        EmptyRES response = new EmptyRES();
        String user = request.getUsername();
        String pass = request.getPassword();
        int profile = request.getProfile();
        if (daoFactory.getUserDao().verifyLoginData(user, pass, profile).equals("false")) {
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        } else {
            response.setMessage("SUCCESS");
            response.setErrorCode("0");
            response.setEsito(true);
        }
        return response;
    }
}
