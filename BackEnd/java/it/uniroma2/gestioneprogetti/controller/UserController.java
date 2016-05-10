package it.uniroma2.gestioneprogetti.controller;

import it.uniroma2.gestioneprogetti.domain.User;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.UserProfilesRQS;
import it.uniroma2.gestioneprogetti.request.UserRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindUsersRES;
import it.uniroma2.gestioneprogetti.response.UserRES;
import it.uniroma2.gestioneprogetti.services.IServiceFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.*;

/**
 *
 * @author Team Talocci
 */
public class UserController {

    //Qui di seguito viene iniettata la dipendenza della ServiceFactory
    @Autowired
    private IServiceFactory serviceFactory;
    private final static String LAYERLBL = "****REST CONTROLLER LAYER**** ";
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());

    /**
     * Il metodo insertUser intercetta le richieste del Front-End per creare nel database un nuovo
     * record nella tabella users prendendo i valori che servono dal body della richiesta passato in ingresso.
     * Angular inserirà all'interno della richiesta HTTP un JSON contenente: un oggetto di tipo User ed una lista 
     * contentente numeri interi, ad indicare i profili da associare con il futuro utente. Inoltre
     * viene inserito anche l'id della sessione.
     * Viene quindi creato l'oggetto UserProfilesRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * Se la response contiene "SUCCESS" viene restituito l'HTTP status OK,
     * altrimenti se la response contiene "false" lo status restituito è BAD_REQUEST e se contiente "FAIL" lo
     * status restituito è SERVICE_UNAVAILABLE .
     * @param body String
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Davide Vitiello, Lorenzo Bernabei
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity insertUser(@RequestBody String body) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method insertUser");        
        
        //viene parsata la stringa con i dati della richiesta in un oggetto JSONObject
        JSONObject jsonRequest = new JSONObject(body);
        //viene prelevato dall'oggetto JSONObject il valore con chiave "user" che è a sua volta un JSONObject
        JSONObject jsonUser = jsonRequest.getJSONObject("user");
        //ricavo l'utente
        UserRQS userRequest = new UserRQS(jsonUser.getInt("id"), jsonUser.getString("username"),
                                          jsonUser.getString("password"), jsonUser.getString("mail"),
                                          jsonUser.getString("name"), jsonUser.getString("surname"),
                                          jsonUser.getString("skill"), jsonUser.getBoolean("isDeactivated"));
        //viene prelevato dall'oggetto JSONObject il valore con chiave "profiles" che è un JSONArray
        JSONArray jsonProfiles = jsonRequest.getJSONArray("profiles");
        //il ciclo mi permette di ricavare tutti i valori del JSONArray e metterli in una lista
        int[] profilesRequest = new int[jsonProfiles.length()];
        for (int i = 0; i < jsonProfiles.length(); i++) {
            profilesRequest[i] = jsonProfiles.getInt(i);
        }
        //viene prelevato dall'oggetto JSONObject il valore con chiave "sessionId"
        String sessionId = jsonRequest.getString("sessionId");        
        
        if (!SessionController.verify(sessionId))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //la sessione è expired

        UserProfilesRQS request = new UserProfilesRQS(userRequest, profilesRequest);
        EmptyRES response = serviceFactory.getUserService().insertUser(request);
        if (response.getMessage().equals("FAIL"))
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE); 
        if(response.getMessage().equals("false"))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity(HttpStatus.CREATED);       

    }

    /**
     * Il metodo updateUser intercetta le richieste del Front-End per aggiornare nel database l'utente
     * con id passato nell'url della richiesta prendendo i valori che servono dal body della richiesta passato in ingresso.
     * Angular inserirà all'interno della richiesta HTTP un JSON contenente: un oggetto di tipo User ed una lista 
     * contentente numeri interi, ad indicare i nuovi profili da associare con l'utente. Inoltre
     * viene inserito anche l'id della sessione.
     * Viene quindi creato l'oggetto UserProfilesRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * Se la response contiene "SUCCESS" viene restituito l'HTTP status OK,
     * altrimenti se la response contiene "false" lo status restituito è BAD_REQUEST e se contiente "FAIL" lo
     * status restituito è SERVICE_UNAVAILABLE .
     * @param body String
     * @param idUser int
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Davide Vitiello, Lorenzo Bernabei
     */
    @RequestMapping(value = "/users/{idUser}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody String body, @PathVariable("idUser") int idUser) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method updateUser");        
        
        //viene parsata la stringa con i dati della richiesta in un oggetto JSONObject
        JSONObject jsonRequest = new JSONObject(body);
        //viene prelevato dall'oggetto JSONObject il valore con chiave "user" che è a sua volta un JSONObject
        JSONObject jsonUser = jsonRequest.getJSONObject("user");
        //ricavo l'utente
        UserRQS userRequest = new UserRQS(idUser, jsonUser.getString("username"),
                                          jsonUser.getString("password"), jsonUser.getString("mail"),
                                          jsonUser.getString("name"), jsonUser.getString("surname"),
                                          jsonUser.getString("skill"), jsonUser.getBoolean("isDeactivated"));
        //viene prelevato dall'oggetto JSONObject il valore con chiave "profiles" che è un JSONArray
        JSONArray jsonProfiles = jsonRequest.getJSONArray("profiles");
        //il ciclo mi permette di ricavare tutti i valori del JSONArray e metterli in una lista
        int[] profilesRequest = new int[jsonProfiles.length()];
        for (int i = 0; i < jsonProfiles.length(); i++) {
            profilesRequest[i] = jsonProfiles.getInt(i);
        }
        //viene prelevato dall'oggetto JSONObject il valore con chiave "sessionId"
        String sessionId = jsonRequest.getString("sessionId");        
        
        if (!SessionController.verify(sessionId)) 
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //la sessione è expired

        UserProfilesRQS request = new UserProfilesRQS(userRequest, profilesRequest);
        EmptyRES response = serviceFactory.getUserService().updateUser(request);
        if (response.getMessage().equals("FAIL"))
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE); 
        if(response.getMessage().equals("false"))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity(HttpStatus.CREATED);       

    }
    
    /**
     * Il metodo intercetta le richieste del Front-End per ottenere la lista degli 
     * utenti presente nel database. Viene dapprima inviata allo strato dei servizi una EmptyRQS. 
     * In output invece viene restituita una risposta HTTP contenente la lista degli utenti
     * che verrà mappata in JSON tramite Angular.
     * @param sessionId String ID della sesssione corrente
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Davide Vitiello
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> displayUsers(@RequestBody String sessionId) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method displayUsers");
        EmptyRQS request = new EmptyRQS(); //Creo una richiesta vuota
        if (!SessionController.verify(sessionId)) //Se la sessione è expired
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        //FindUsersRES è un oggetto di appoggio che traporta al suo interno la lista degli utenti
        FindUsersRES response = serviceFactory.getUserService().displayUsers(request);
        //qui di seguito la lista viene presa da FindUsersRES e memorizzata in una nuova lista
        List<User> users = new ArrayList<>();
        //conversione da lista di interi da array di interi
        for (UserRES userFromRes : response.getUsersList()) {
            User user = new User(userFromRes.getId(), userFromRes.getUsername(), userFromRes.getPassword(),
                             userFromRes.getEmail(), userFromRes.getName(), userFromRes.getSurname(),
                             userFromRes.getSkill(), userFromRes.getIsDeactivated());
            users.add(user);
        }
        //Esito negativo delle operazioni
        if(!response.isEsito()){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }     
        //Esito posiivo delle operazioni
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    /**
     * Il metodo intercetta le richieste del Front-End che richiedono di effettuare il login per un determinato
     * utente. Viene quindi creato l'oggetto UserRQS che incapsula la richiesta e viene memorizzata la response
     * in un EmptyRES. Se la response restituita dal servizio del livello sottostante contiene "SUCCESS",
     * viene creata una sessione per l'utente che ha effettuato il Login e viene restituito l'HTTP status OK,
     * altrimenti se la response contiene "false" lo status restituito è BAD_REQUEST e se contiente "FAIL" lo
     * status restituito è SERVICE_UNAVAILABLE .
     * @param body String
     * @return ResponseEntity response
     * @author L.Camerlengo
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody String body){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest user controller method login");
        JSONObject jsonRequest = new JSONObject(body);
        String username = jsonRequest.getString("username");
        String password = jsonRequest.getString("password");
        int profile = jsonRequest.getInt("profile");
        UserRQS request = new UserRQS();
        request.setUsername(username);
        request.setPassword(password);
        request.setProfile(profile);
        EmptyRES response = serviceFactory.getUserService().verifyLoginData(request);
        if(response.getMessage().equals("FAIL"))
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        if(response.getMessage().equals("false"))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        SessionController.add();
        return new ResponseEntity(HttpStatus.OK);      
    }
}