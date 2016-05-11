package it.uniroma2.gestioneprogetti.controller;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindProjectsRES;
import it.uniroma2.gestioneprogetti.response.ProjectRES;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Team Talocci
 */
@RestController
public class ProjectController {
    
    @Autowired
    private IServiceFactory serviceFactory;
    private final static String LAYERLBL = "****REST CONTROLLER LAYER**** ";
    private final static Logger LOGGER = Logger.getLogger(ProjectController.class.getName());
    
    /**
     * Il metodo intercetta le richieste del Front-End che richiedono di cancellare il progetto che
     * ha associato l'id passato nell'url della richiesta. Inoltre nel corpo della richiesta viene passato
     * un id di sessione. Entrabi questi parametri sono passati in ingresso al metodo.
     * Viene quindi creato l'oggetto ProjectRQS che incapsula la richiesta e prima di chiamare il servizio del livello
     * sottostante viene verificato se l'utente ha una sessione attiva. In caso di esito negativo viene restituito
     * l' http status UNAUTHORIZED altrimenti viene memorizzata la response in un EmptyRES. Se l'esito contenuto nella response
     * e' positivo viene restituito l'http status OK, altrimenti viene restituito lo status SERVICE_UNAVAILABLE. 
     * @param idProject int
     * @param sessionId String
     * @return ResponseEntity response
     * @author L.Camerlengo
     */
    @RequestMapping(value = "/projects/{idProject}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@RequestBody String sessionId, @PathVariable("id") int idProject){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest project controller method deleteProject");
        if(SessionController.verify(sessionId) == false) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        ProjectRQS request = new ProjectRQS();
        request.setId(idProject);
        EmptyRES response = serviceFactory.getProjectService().deleteProject(request);
        if(response.isEsito()){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }      
    }

    /**
     * Il metodo displayProjects viene chiamato dal ServletDispatcher in caso di match con il mapping
     * definito dall'annotazione @RequestMapping. Il corpo della richiesta HTTP contiene l'id della
     * sessione, che viene passato in ingresso al metodo.
     * Come prima cosa viene verificato se l'utente ha una sessione attiva. In caso di esito negativo
     * viene restituito l'HTTP status UNAUTHORIZED.
     * Altrimenti, viene creato l'oggetto EmpyRQS che rappresenta la richiesta priva di dati e viene
     * chiamato l'appropriato servizio del livello sottostante.
     * Se l'esito contenuto nella response e' positivo vengono restituiti i dati con l'HTTP status OK,
     * altrimenti viene restituito lo status SERVICE_UNAVAILABLE.
     * @param sessionId String
     * @return ResponseEntity response contenente i dati del body e/o lo status HTTP
     * @author Lorenzo Bernabei
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> displayProjects(@RequestBody String sessionId){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest project controller method displayProjects");
        List<Project> projects = new ArrayList<>();
        
        if(!SessionController.verify(sessionId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        EmptyRQS request = new EmptyRQS();
        FindProjectsRES response = serviceFactory.getProjectService().displayProjects(request);
        
        for (ProjectRES projectRes : response.getProjectsList()) {
            Project project = new Project(projectRes.getId(), projectRes.getName(), projectRes.getDescription(),
                                          projectRes.getStatus(), projectRes.getBudget(), projectRes.getCost(),
                                          projectRes.getProjectManager());
            projects.add(project);
        }
        
        if(response.isEsito())
            return new ResponseEntity<>(projects, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);     
    }

     /**
     * Il metodo insertProject intercetta le richieste del Front-End per creare nel database un nuovo
     * record nella tabella projects prendendo i valori che servono dal body della richiesta passato in ingresso.
     * Angular inserirà all'interno della richiesta HTTP un JSON contenente: un oggetto di tipo Project.
     * Inoltre viene inserito anche l'id della sessione.
     * Viene quindi creato l'oggetto ProjectRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * Se la response contiene "SUCCESS" viene restituito l'HTTP status OK,
     * altrimenti se la response contiene "false" lo status restituito è BAD_REQUEST e se contiente "FAIL" lo
     * status restituito è SERVICE_UNAVAILABLE .
     * @param body String
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Lorenzo Svezia
     */
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity insertProject(@RequestBody String body) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method insertProject");        
        
        //viene parsata la stringa con i dati della richiesta in un oggetto JSONObject
        
	JSONObject jsonRequest = new JSONObject(body);

        //viene prelevato dall'oggetto JSONObject il valore con chiave "user" che è a sua volta un JSONObject
        
	JSONObject jsonProject = jsonRequest.getJSONObject("project");
        
	//ricavo il progetto

        ProjectRQS projectRequest = new ProjectRQS(jsonproject.getInt("id"), jsonProject.getString("name"),
                                          jsonProject.getString("description"), jsonProject.getString("status"),
                                          jsonProject.getDouble("budget"), jsonProject.getDouble("cost"),
                                          jsonProject.getInt("projectManager"));

        
        //viene prelevato dall'oggetto JSONObject il valore con chiave "sessionId"
        String sessionId = jsonRequest.getString("sessionId");        
        
        if (!SessionController.verify(sessionId))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //la sessione è expired

        ProjectRQS request = new ProjectRQS(projectRequest);
        EmptyRES response = serviceFactory.getProjectService().insertProject(request);
        if (response.getMessage().equals("FAIL"))
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE); 
        if(response.getMessage().equals("false"))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity(HttpStatus.CREATED);       

    }


     /**
     * Il metodo updateProject intercetta le richieste del Front-End per aggiornare nel database il progetto
     * con id passato nell'url della richiesta prendendo i valori che servono dal body della richiesta passato in ingresso.
     * Angular inserirà all'interno della richiesta HTTP un JSON contenente: un oggetto di tipo Project.
     * Inoltre viene inserito anche l'id della sessione.
     * Viene quindi creato l'oggetto ProjectRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * Se la response contiene "SUCCESS" viene restituito l'HTTP status OK,
     * altrimenti se la response contiene "false" lo status restituito è BAD_REQUEST e se contiente "FAIL" lo
     * status restituito è SERVICE_UNAVAILABLE .
     * @param body String
     * @param idProject int
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Lorenzo Svezia
     */
    @RequestMapping(value = "/projects/{idProject}", method = RequestMethod.PUT)
    public ResponseEntity updateProject(@RequestBody String body, @PathVariable("idProject") int idProject) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method updateProject");        
        
        //viene parsata la stringa con i dati della richiesta in un oggetto JSONObject
        JSONObject jsonRequest = new JSONObject(body);
        //viene prelevato dall'oggetto JSONObject il valore con chiave "project" che è a sua volta un JSONObject
        JSONObject jsonProject = jsonRequest.getJSONObject("project");
        //ricavo il progetto
        ProjectRQS projectRequest = new ProjectRQS(idProject, jsonProject.getString("name"),
                                          jsonProject.getString("description"), jsonProject.getString("status"),
                                          jsonProject.getDouble("budget"), jsonProject.getDouble("cost"),
                                          jsonProject.getInt("projectManager"));

     
        //viene prelevato dall'oggetto JSONObject il valore con chiave "sessionId"
        String sessionId = jsonRequest.getString("sessionId");        
        
        if (!SessionController.verify(sessionId)) 
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //la sessione è expired

        ProjectRQS request = new ProjectRQS(projectRequest);
        EmptyRES response = serviceFactory.getProjectService().updateProject(request);
        if (response.getMessage().equals("FAIL"))
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE); 
        if(response.getMessage().equals("false"))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity(HttpStatus.CREATED);       

    }

}
