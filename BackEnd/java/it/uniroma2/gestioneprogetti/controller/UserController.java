package it.uniroma2.gestioneprogetti.controller;

import it.uniroma2.gestioneprogetti.domain.User;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.UserProfilesRQS;
import it.uniroma2.gestioneprogetti.request.UserRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindUserRES;
import it.uniroma2.gestioneprogetti.response.UserRES;
import it.uniroma2.gestioneprogetti.services.IServiceFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Davide Vitiello
 */
public class UserController {

    //Qui di seguito viene iniettata la dipendenza della ServiceFactory
    @Autowired
    private IServiceFactory serviceFactory;
    private final static String LAYERLBL = "****REST CONTROLLER LAYER**** ";
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());

    /*
     * Il metodo insertUser intercetta le richieste del Front-End per creare nel database un nuovo
     * record nella tabella users prendendo i valori che servono dal parametro in ingresso:
     * Angular inserirà all'interno della richiesta http un oggetto di tipo User ed una lista 
     * contentente numeri interi, ad indicare i profili da associare con il futuro utente.
     * Viene quindi creato l'oggetto UserProfilesRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * @param user User oggetto utente da inserire
     * @param profiles ArrayList<Integer> i profili da associare all'utente da inserire
     * @param session String ID della sesssione corrente
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Davide Vitiello
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity insertUser(@RequestBody User user, ArrayList<Integer> profiles, @PathVariable("session") String session) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method insertUser con username:{0}", user.getName());
        UserRQS userRequest = new UserRQS(0, user.getName());
        if (!SessionController.verify(session)) {//Se la sessione è expired
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        } else {//se la sessione è valida
            //conversione da lista di interi da array di interi
            int[] profilesArray = ArrayUtils.toPrimitive(Arrays.copyOf(profiles.toArray(), profiles.toArray().length, Integer[].class));
            UserProfilesRQS request = new UserProfilesRQS(userRequest, profilesArray);
            EmptyRES res = serviceFactory.getUserService().insertUser(request);
            if (res.isEsito()) //Esito negativo delle operazioni
            {
                return new ResponseEntity(HttpStatus.CREATED);
            } else //Esito positivo delle operazioni
            {
                return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

    }


/*
     * Il metodo updateUser intercetta le richieste del Front-End per modificare nel database
     * un record nella tabella users ed eventualmente,qualora vengono modificate le associazioni
     * dell'utente con i profili,della tabella profileuser, prendendo i valori che servono dal parametro in ingresso:
     * Angular inserirà all'interno della richiesta http un oggetto di tipo User ed una lista 
     * contentente numeri interi, ad indicare i nuovi profili da associare con ò'utente da modificare.
     * Viene quindi creato l'oggetto UserProfilesRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * @param user User oggetto utente da modificare
     * @param profiles ArrayList<Integer> i profili da associare all'utente 
     * @param session String ID della sesssione corrente
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Davide Vitiello
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user, ArrayList<Integer> profiles, @PathVariable("session") String session) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method updateUser con username:{0}", user.getName());
        UserRQS userRequest = new UserRQS(0, user.getName());
        if (!SessionController.verify(session)) {//Se la sessione è expired
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        } else {//se la sessione è valida
            //conversione da lista di interi da array di interi
            int[] profilesArray = ArrayUtils.toPrimitive(Arrays.copyOf(profiles.toArray(), profiles.toArray().length, Integer[].class));
            UserProfilesRQS request = new UserProfilesRQS(userRequest, profilesArray);
            EmptyRES res = serviceFactory.getUserService().updateUser(request);
            if (res.isEsito()) //Esito negativo delle operazioni
            {
                return new ResponseEntity(HttpStatus.CREATED);
            } else //Esito positivo delle operazioni
            {
                return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

    }
    
    /*
     * Il metodo intercetta le richieste del Front-End per ottenere la lista degli 
     * utenti presente nel database. Viene dapprima inviata allo strato dei servizi una EmptyRQS. 
     * In output invece viene restituita una risposta http contenente la lista degli utenti
     * che verrà mappata in json tramite Angular.
     * @param session String ID della sesssione corrente
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Davide Vitiello
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> displayUsers(@PathVariable("session") String session) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method displayUsers");
        EmptyRQS request = new EmptyRQS(); //Creo una richiesta vuota
        //FindUsersRES è un oggetto fantoccio che traporta al suo interno la lista degli utenti
        FindUserRES response = serviceFactory.getUserService().displayUsers(request);
        //qui di seguito la lista viene presa da FindUsersRES e memorizzata in una nuova lista
        List<User> users = new ArrayList<User>();
        if (!SessionController.verify(session)) {//Se la sessione è expired
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        } else {//se la sessione è valida
            //conversione da lista di interi da array di interi
        for (UserRES userFromRes : response.getUsersList()) {
            User user = new User(userFromRes.getId(), userFromRes.getUsername(), userFromRes.getPassword(),
                             userFromRes.getEmail(), userFromRes.getName(), userFromRes.getSurname(),
                             userFromRes.getSkill(), userFromRes.getIsDeactivated());
            users.add(user);
        }
        //Esito negativo delle operazioni
        if(!response.isEsito()){
            return new ResponseEntity<List<User>>(users, HttpStatus.SERVICE_UNAVAILABLE);
        }     
        //Esito posiivo delle operazioni
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }}
    
    /**
     * Il metodo intercetta le richieste del Front-End che richiedono di effettuare il login per un determinato
     * utente. Viene quindi creato l'oggetto UserRQS che incapsula la richiesta e viene memorizzata la response
     * in un EmptyRES. Se la response restituita dal servizio del livello sottostante contiene un esito positivo, viene creata una
     * sessione per l'utente che ha effettuato il Login e viene restituito l'http status OK, altrimenti lo status
     * restituito è SERVICE_UNAVAILABLE.
     * @param username String
     * @param password String
     * @param profile String
     * @return ResponseEntity response
     * @author L.Camerlengo
     */
    @RequestMapping(value="/",method=RequestMethod.POST)
    public ResponseEntity login(@PathVariable("username") String username,@PathVariable("password") String password,@PathVariable("profile")int profile){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest user controller method login");
        UserRQS request=new UserRQS();
        request.setUsername(username);
        request.setPassword(password);
        request.setProfile(profile);
        EmptyRES response=serviceFactory.getUserService().verifyLoginData(request);
        if(!response.isEsito()){
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
        SessionController.add(String.valueOf(profile));
        return new ResponseEntity(HttpStatus.OK);      
    }
}