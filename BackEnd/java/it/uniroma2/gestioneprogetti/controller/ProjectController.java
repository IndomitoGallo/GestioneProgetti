package it.uniroma2.gestioneprogetti.controller;

import it.uniroma2.gestioneprogetti.bean.ProjectEmployeesBean;
import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectEmployeesRQS;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *La classe ProjectController intercetta tutte le richieste del Front-End
 *che sono inerenti alla gestione dei progetti.
 *Essa si occupa quindi di gestire le richieste e chiamare i servizi della
 *classe ProjectService e fornire in risposta un messaggio HTTP contente in alcuni
 *casi un oggetto Model.
 * @author Team Talocci
 */
@RestController
public class ProjectController {
    
    @Autowired
    private IServiceFactory serviceFactory;
    private final static String LAYERLBL = "****REST CONTROLLER LAYER**** ";
    private final static Logger LOGGER = Logger.getLogger(ProjectController.class.getName());
    
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
    public ResponseEntity<List<Project>> displayProjects(@RequestParam String sessionId){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method displayProjects");
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
     * Angular inserira' all'interno della richiesta HTTP un JSON contenente: un oggetto di tipo Projecte e una
     * lista di numeri interi, ad indicare i dipendenti da associare con il futuro progetto.
     * Inoltre viene inserito anche l'id della sessione.
     * Viene quindi creato l'oggetto ProjectEmployeesRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * Se la response contiene "SUCCESS" viene restituito l'HTTP status OK,
     * altrimenti se la response contiene "false" lo status restituito e' BAD_REQUEST e se contiene "FAIL" lo
     * status restituito e' SERVICE_UNAVAILABLE .
     * @param peb ProjectEmployeesBean
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Lorenzo Svezia
     */
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity insertProject(@RequestBody ProjectEmployeesBean peb) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method insertProject");        
        
        Project project = peb.getProject();
        int[] employees = peb.getEmployees();
        String sessionId = peb.getSessionId();
        
        ProjectRQS projectRequest = new ProjectRQS(project.getId(),project.getName(),project.getDescription(),
                                                   project.getStatus(),project.getBudget(),project.getCost(),
                                                   project.getProjectManager());        
        
        if (!SessionController.verify(sessionId))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //la sessione e' expired

        ProjectEmployeesRQS request = new ProjectEmployeesRQS(projectRequest, employees);
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
     * Angular inserira' all'interno della richiesta HTTP un JSON contenente: un oggetto di tipo Project e una
     * lista di numeri interi, ad indicare i nuovi dipendenti da associare con il progetto.
     * Inoltre viene inserito anche l'id della sessione.
     * Viene quindi creato l'oggetto ProjectEmployeesRQS per trasportare le informazioni verso lo 
     * strato dei servizi e viene memorizzato l'esito dell'operazione in un'EmptyRES.
     * Se la response contiene "SUCCESS" viene restituito l'HTTP status OK,
     * altrimenti se la response contiene "false" lo status restituito e' BAD_REQUEST e se contiente "FAIL" lo
     * status restituito e' SERVICE_UNAVAILABLE.
     * @param peb ProjectEmployeesBean
     * @param idProject int
     * @return ResponseEntity risposta HTTP contentente l'esito dell'operazione, viene passata allo strato superiore
     * @author Lorenzo Svezia
     */
    @RequestMapping(value = "/projects/{idProject}", method = RequestMethod.PUT)
    public ResponseEntity updateProject(@RequestBody ProjectEmployeesBean peb, @PathVariable("idProject") int idProject) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method updateProject");        
        
        Project project = peb.getProject();
        int[] employees = peb.getEmployees();
        String sessionId = peb.getSessionId();
        
        ProjectRQS projectRequest = new ProjectRQS(idProject,project.getName(),project.getDescription(),
                                                   project.getStatus(),project.getBudget(),project.getCost(),
                                                   project.getProjectManager());
        
        if (!SessionController.verify(sessionId)) 
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //la sessione e' expired
        
        ProjectEmployeesRQS request = new ProjectEmployeesRQS(projectRequest, employees);
        EmptyRES response = serviceFactory.getProjectService().updateProject(request);
        if (response.getMessage().equals("FAIL"))
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE); 
        if(response.getMessage().equals("false"))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
