package it.uniroma2.gestioneprogetti.controller;

import it.uniroma2.gestioneprogetti.bean.ProjectEmployeesBean;
import it.uniroma2.gestioneprogetti.bean.ProjectEmployeesHoursBean;
import it.uniroma2.gestioneprogetti.bean.ProjectFormBean;
import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectEmployeesRQS;
import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindProjectsRES;
import it.uniroma2.gestioneprogetti.response.PMsEmployeesRES;
import it.uniroma2.gestioneprogetti.response.ProjectEmployeesHoursRES;
import it.uniroma2.gestioneprogetti.response.ProjectFormRES;
import it.uniroma2.gestioneprogetti.response.ProjectRES;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * La classe ProjectController intercetta tutte le richieste del Front-End
 * che sono inerenti alla gestione dei progetti.
 * Essa si occupa quindi di gestire le richieste e chiamare i servizi della
 * classe ProjectService e fornire in risposta un messaggio HTTP contente in alcuni
 * casi un oggetto Model.
 * @author Team Talocci
 */
@RestController
public class ProjectController {
    
    @Autowired
    private IServiceFactory serviceFactory;
    private final static String LAYERLBL = "****REST CONTROLLER LAYER**** ";
    private final static Logger LOGGER = Logger.getLogger(ProjectController.class.getName());
    
    /**
     * Il metodo displayProjects viene chiamato dal ServletDispatcher per fare una retrieve di tutti
     * i progetti presenti nel database. Il corpo della richiesta HTTP contiene l'id della sessione,
     * che viene passato in ingresso al metodo.
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
     * Come prima cosa viene verificato se l'utente ha una sessione attiva. In caso di esito negativo
     * viene restituito l'HTTP status UNAUTHORIZED.
     * Altrimenti, viene creato l'oggetto ProjectEmployeesRQS per trasportare le informazioni verso lo 
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
     * Come prima cosa viene verificato se l'utente ha una sessione attiva. In caso di esito negativo
     * viene restituito l'HTTP status UNAUTHORIZED.
     * Altrimenti, viene creato l'oggetto ProjectEmployeesRQS per trasportare le informazioni verso lo 
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

    /**
     * Il metodo displayProjectForm intercetta le richieste del Front-End per fare
     * una retrieve del progetto con id passato nell'url della richiesta e delle sue
     * associazioni oltre che una retrieve dei PM e dei Dipendenti, per essere poi
     * visualizzati nella form di update di un progetto.
     * Angular inserirà all'interno della richiesta HTTP anche l'id della sessione.
     * Come prima cosa viene controllato se l'utente ha una sessione attiva. In caso
     * di esito negativo viene restituito l'HTTP status UNAUTHORIZED.
     * Altrimenti, viene creato l'oggetto ProjectRQS per trasportare le informazioni verso
     * lo strato dei servizi e vengono memorizzati i dati con l'esito dell'operazione
     * in un oggetto ProjectFormRES. Se la response contiene "SUCCESS" viene restituito
     * l'HTTP status OK, altrimenti se la response contiente "FAIL" lo status restituito
     * è SERVICE_UNAVAILABLE.
     * @param sessionId String
     * @param idProject int
     * @return ResponseEntity response contenente i dati del body e/o lo status HTTP
     * @author Lorenzo Bernabei
     */
    @RequestMapping(value = "/projects/form/{idProject}", method = RequestMethod.GET)
    public ResponseEntity<ProjectFormBean> displayProjectForm(@RequestParam String sessionId, @PathVariable("idProject") int idProject){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method displayProjectForm");
        ProjectFormBean pfb = new ProjectFormBean();
        
        if(!SessionController.verify(sessionId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        ProjectRQS request = new ProjectRQS();
        request.setId(idProject);
        ProjectFormRES response = serviceFactory.getProjectService().displayProjectForm(request);
        
        if(!response.isEsito())
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);            
        
        ProjectRES projectRes = response.getProject();
        Project project = new Project(projectRes.getId(), projectRes.getName(), projectRes.getDescription(),
                                      projectRes.getStatus(), projectRes.getBudget(), projectRes.getCost(),
                                      projectRes.getProjectManager());
        
        List<User> employeesList = new ArrayList<>();
        for (UserRES u : response.getEmployees()){
            User user = new User(u.getId(), u.getUsername(), u.getPassword(),
                                 u.getEmail(), u.getName(), u.getSurname(),
                                 u.getSkill(), u.getIsDeactivated());
            employeesList.add(user);
        }
        
        List<User> pmsList = new ArrayList<>();
        for (UserRES u : response.getPms()){
            User user = new User(u.getId(), u.getUsername(), u.getPassword(),
                                 u.getEmail(), u.getName(), u.getSurname(),
                                 u.getSkill(), u.getIsDeactivated());
            pmsList.add(user);
        }
        
        pfb.setProject(project);
        pfb.setEmployeesAssociation(response.getEmpolyeesAssociation());
        pfb.setEmployees(employeesList);
        pfb.setPms(pmsList);

        return new ResponseEntity<>(pfb, HttpStatus.OK);
                
    }   

    /**
     * Il metodo displayPMsEmployees intercetta le richieste del Front-End per mostrare la lista di
     * dipendenti e project manager presenti all'interno del database.
     * Angular inserirà all'interno della richiesta HTTP anche l'id della sessione.
     * Come prima cosa viene verificato se l'utente ha una sessione attiva. In caso di esito negativo
     * viene restituito l'HTTP status UNAUTHORIZED.
     * Altrimenti, viene creato l'oggetto EmpyRQS che rappresenta la richiesta priva di dati e viene
     * chiamato l'appropriato servizio del livello sottostante e viene memorizzato l'esito in un oggetto
     * PMsEmployeesRES.
     * Se la response contiene "SUCCESS" viene restituita una risposta HTTP contenente lo status OK e la
     * lista dei dipendenti e dei projectManager che verranno mappate in JSON tramite Angular, altrimenti
     * se la response contiene "FAIL" viene ristituita una risposta HTTP contenente lo status
     * SERVICE_UNAVAILABLE.
     * 
     * @param sessionId String
     * @return ResponseEntity response contenente i dati del body e/o lo status HTTP
     * @author L.Camerlengo
     */
    @RequestMapping(value = "/projects/pmsemployees", method = RequestMethod.GET)
    public ResponseEntity<List<List<User>>> displayPMsEmployees(@RequestParam String sessionId) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method displayPMsEmployees");
        List<List<User>> users = null;
        if (!SessionController.verify(sessionId)) {
            return new ResponseEntity<>(users, HttpStatus.UNAUTHORIZED); //la sessione e' expired
        }
        EmptyRQS request = new EmptyRQS();
        PMsEmployeesRES response = serviceFactory.getProjectService().displayPMsEmployees(request);
        if (response.getMessage().equals("FAIL")) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        users = new ArrayList<>();
        List<UserRES> employeesRES = response.getEmployeesList();
        List<User> employees = new ArrayList<>();
        for (UserRES u : employeesRES) {
            User user = new User(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(), u.getName(), u.getSurname(), u.getSkill(), u.getIsDeactivated());
            employees.add(user);
        }
        List<UserRES> pmsRES = response.getPmsList();
        List<User> pms = new ArrayList<>();
        for (UserRES u : pmsRES) {
            User user = new User(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(), u.getName(), u.getSurname(), u.getSkill(), u.getIsDeactivated());
            pms.add(user);
        }
        users.add(employees);
        users.add(pms);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }    
    
    
    /**
     * Il metodo displayPMProjects viene chiamato dal ServletDispatcher per fare una retrieve dei
     * progetti del PM che l'ha invocato. La richiesta HTTP contiene l'id della sessione, che viene
     * passato in ingresso al metodo.
     * Come prima cosa viene verificato se l'utente ha una sessione attiva. In caso di esito negativo
     * viene restituito l'HTTP status UNAUTHORIZED.
     * Altrimenti, dall'id sessione si ottiene l'id del PM, viene creato l'oggetto ProjectRQS che rappresenta
     * la richiesta con l'id del PM e viene chiamato l'appropriato servizio del livello sottostante.
     * Se l'esito contenuto nella response e' positivo vengono restituiti i dati con l'HTTP status OK,
     * altrimenti viene restituito lo status SERVICE_UNAVAILABLE.
     * @param sessionId String
     * @return ResponseEntity response contenente i dati del body e/o lo status HTTP
     * @author Lorenzo Bernabei
     */
    @RequestMapping(value = "/projects/pm", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> displayPMProjects(@RequestParam String sessionId){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method displayPMProjects");
        List<Project> projects = new ArrayList<>();
        
        if(!SessionController.verify(sessionId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        int idPM = SessionController.getUserId(sessionId);
        
        ProjectRQS request = new ProjectRQS();
        request.setProjectManager(idPM);
        FindProjectsRES response = serviceFactory.getProjectService().displayPMProjects(request);
        
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
     * Il metodo displayPMProject viene chiamato dal ServletDispatcher per fare una retrieve del
     * progetto con id passato nell'url della richiesta relativo al PM che l'ha invocato. Oltre
     * alle informazioni sul progetto vengono anche recuperate le associazioni con i Dipendenti e
     * il totale delle ore lavorate.
     * La richiesta HTTP contiene l'id della sessione, che viene passato in ingresso al metodo.
     * Come prima cosa viene verificato se l'utente ha una sessione attiva. In caso di esito negativo
     * viene restituito l'HTTP status UNAUTHORIZED.
     * Altrimenti, dall'id sessione si ottiene l'id del PM, viene creato l'oggetto ProjectRQS che
     * rappresenta la richiesta con l'id del PM e l'id del progetto e viene chiamato l'appropriato
     * servizio del livello sottostante.
     * Se l'esito contenuto nella response e' positivo vengono restituiti i dati con l'HTTP status OK,
     * altrimenti viene restituito lo status SERVICE_UNAVAILABLE.
     * @param sessionId String
     * @param idProject String
     * @return ResponseEntity response contenente i dati del body e/o lo status HTTP
     * @author Lorenzo Bernabei
     */
    @RequestMapping(value = "/projects/pm/{idProject}", method = RequestMethod.GET)
    public ResponseEntity<ProjectEmployeesHoursBean> displayPMProject(@RequestParam String sessionId, @PathVariable("idProject") int idProject){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest controller method displayPMProject");
        
        if(!SessionController.verify(sessionId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        int idPM = SessionController.getUserId(sessionId);
        
        ProjectRQS request = new ProjectRQS();
        request.setId(idProject);        
        request.setProjectManager(idPM);
        
        ProjectEmployeesHoursRES response = null;//serviceFactory.getProjectService().displayPMProject(request);
        
        if(!response.isEsito())
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE); 
        
        ProjectRES projectRes = response.getProject();
        Project project = new Project(projectRes.getId(), projectRes.getName(), projectRes.getDescription(),
                                      projectRes.getStatus(), projectRes.getBudget(), projectRes.getCost(),
                                      projectRes.getProjectManager());
        ProjectEmployeesHoursBean pehb = new ProjectEmployeesHoursBean(project, response.getEmployees(), response.getHours(), ""); //il nome del PM non viene specificato
        
        return new ResponseEntity<>(pehb, HttpStatus.OK);                
    }
}
