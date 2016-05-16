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
import it.uniroma2.gestioneprogetti.response.FindUsersRES;
import it.uniroma2.gestioneprogetti.response.UserProfilesRES;
import it.uniroma2.gestioneprogetti.response.UserRES;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * 
 * @author Team Talocci
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
        
        result = daoFactory.getUserDao().updateProfilesAssociation(user.getId(), profiles);
        
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
     * Il metodo deleteUser prende l'oggetto UserRQS proveniente dallo strato "Application", ovvero quello
     * del Controller, esso contiene l'id dell'utente da eliminare. Ricordiamo che l'eliminazione è
     * solo logica, ovvero l'utente viene disattivato e non cancellato fisicamente dal database!
     * Questo metodo sfrutta i metodi forniti dallo strato "Domain" per controllare i profili dell'utente.
     * Se esso non è un PM allora viene disattivato e vengono cancellati i timesheet. Se invece l'utente è
     * un PM si controlla che non abbia progetti con status a "in corso". Se il controllo va a buon fine viene
     * disattivato e, qualora sia anche un Dipendente, vengono cancellati i timesheet. Se il controllo ha esito
     * negativo allora la richiesta viene considerata errata, perchè non può essere cancellato un PM con 
     * progetti "in corso"!
     * L'esito dell'operazione viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato)
     * all'interno del quale viene settato un messaggio di fallimento o successo.
     * @param request UserRQS oggetto che incapsula i dati della richiesta
     * @return EmptyRES oggetto che incapsula l'esito del servizio
     * @author Lorenzo Bernabei
     */
    @Override
    public EmptyRES deleteUser(UserRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio deleteUser");
        EmptyRES response = new EmptyRES();
        int idUser = request.getId();
        String result = null;
        
        String profile = daoFactory.getUserDao().verifyProfiles(idUser);
        
        if (profile.equals("FAIL")) {
            response.setMessage(profile);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        if(profile.equals("DIP")) { //se l'utente non è un PM lo disattivo e cancello il timesheet
            result = daoFactory.getUserDao().deactivateUser(idUser);
            
            if (result.equals("FAIL")) {
                response.setMessage(result);
                response.setErrorCode("1");
                response.setEsito(false);
                return response;            
            }
            
            //result = daoFactory.getUserDao().deleteTimesheet(idUser);
            
            if (result.equals("FAIL")) {
                response.setMessage(result);
                response.setErrorCode("1");
                response.setEsito(false);
                return response;            
            }
        }
        else { //se l'utente è un PM controllo se ha progetti "in corso"
            //result = daoFactory.getUserDao().verifyProjectsStatus(idUser);
            
            if (result.equals("FAIL")) {
                response.setMessage(result);
                response.setErrorCode("1");
                response.setEsito(false);
                return response;            
            }
            
            if(result.equals("true")) { //se non ha progetti "in corso" lo disattivo e cancello il timesheet se è anche Dipendente
                result = daoFactory.getUserDao().deactivateUser(idUser);
            
                if (result.equals("FAIL")) {
                    response.setMessage(result);
                    response.setErrorCode("1");
                    response.setEsito(false);
                    return response;            
                }
                
                if(profile.equals("DIPPM")) {
                    //result = daoFactory.getUserDao().deleteTimesheet(idUser);

                    if (result.equals("FAIL")) {
                        response.setMessage(result);
                        response.setErrorCode("1");
                        response.setEsito(false);
                        return response;            
                    }
                }
            }
            else { //se ha progetti in corso, c'è un errore, non può essere cancellato!
                response.setMessage(result);
                response.setErrorCode("1");
                response.setEsito(false);
                return response; 
            }
        }
        
        response.setMessage("SUCCESS");
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }
    
    /**
     * Il metodo displayUser prende un oggetto UserRQS proveniente dallo strato "Application", ovvero quello
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
    public UserProfilesRES displayUser(UserRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio displayUser");
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
        
        int[] profiles = daoFactory.getUserDao().retrieveProfilesAssociation(user.getId());
        
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
     * viene inizializzato l'oggetto FindUsersRES tramite la lista
     * precedentemente riempita. Infine viene anche settato un messaggio per
     * comunicare l'esito dell'operazione.
     *
     * @param request EmptyRQS oggetto di richiesta "vuoto"
     * @return FindUsersRES oggetto che incapsula la lista di usenti e l'esito
     * del servizio
     * @author Davide Vitiello
     */
    @Override
    public FindUsersRES displayUsers(EmptyRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio displayUsers");
        List<User> usersList = daoFactory.getUserDao().displayUsers();
        FindUsersRES response = new FindUsersRES();

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
     * una response contenente: "FAIL" in caso di eccezioni, "false"
     * nel caso in cui i vincoli non sono stati rispettati e "SUCCESS" se i vincoli
     * sono stati soddisfatti.
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
        String result = daoFactory.getUserDao().verifyLoginData(user, pass, profile);
        
        if (result.equals("false")) {
            response.setMessage("false");
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        } 
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        response.setMessage("SUCCESS");
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }
}
