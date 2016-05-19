package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.dao.IDAOFactory;
import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectEmployeesRQS;
import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindProjectsRES;
import it.uniroma2.gestioneprogetti.response.PMsEmployeesRES;
import it.uniroma2.gestioneprogetti.response.ProjectEmployeesRES;
import it.uniroma2.gestioneprogetti.response.ProjectFormRES;
import it.uniroma2.gestioneprogetti.response.ProjectRES;
import it.uniroma2.gestioneprogetti.response.UserRES;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La classe ProjectService si occupa di gestire la logica applicativa che riguarda
 * i servizi relativi ai progetti.
 * La classe ProjectController accede ai servizi che offre questa classe tramite un oggetto
 * request. Essa dopo aver utilizzato i metodi messi a disposizione dalla classe ProjectDAO 
 * restituisce come risposta un oggetto response.
 * 
 * @author Team Talocci
 */
@Service("ProjectService")
public class ProjectService implements IProjectService {
    
    private final static Logger LOGGER = Logger.getLogger(ProjectService.class.getName());
    private final static String LAYERLBL = "****SERVICE LAYER**** ";
    
    //Qui di seguito viene iniettata la dipendenza della DaoFactory
    @Autowired
    private IDAOFactory daoFactory;
    
    /**
     * Il metodo insertProject prende l'oggetto RQS proveniente dallo strato "Application", ovvero quello
     * del Controller, copia il contenuto di tale oggetto in un nuovo oggetto Project e lancia,
     * tramite la DaoFactory, i metodi insertProject e insertEmployeesAssociation dello strato "Domain".
     * L'esito dell'operazione viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato)
     * all'interno del quale viene settato un messaggio di fallimento o successo.
     * @param request ProjectEmployeesRQS
     * @return EmptyRES response
     * @author L.Camerlengo
     */
    @Override
    public EmptyRES insertProject(ProjectEmployeesRQS request){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio insertProject");
        EmptyRES response = new EmptyRES();
        ProjectRQS projectRequest = request.getProject();
        int[] employees = request.getEmployees();
        
        Project project = new Project(projectRequest.getId(),projectRequest.getName(),projectRequest.getDescription(),
                                     projectRequest.getStatus(),projectRequest.getBudget(),projectRequest.getCost(),
                                     projectRequest.getProjectManager());
        
        String message = daoFactory.getProjectDao().insertProject(project);        

        if(message.equals("FAIL")) {
            response.setMessage(message);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        //message = daoFactory.getProjectDao().insertEmployeesAssociation(project.getId(), employees);
        
        if (message.equals("FAIL")) {
            /*Se viene inserito con successo il progetto ma non i dipendenti
            bisogna abortire l'intera procedura. Si elimina il progetto inserito
            con successo precedentemente, il che si traduce nell'eliminazione
            del record ma anche dei dipendenti associati e inseriti fino a quel
            momento (prima dell'eccezione).*/
            daoFactory.getProjectDao().deleteProject(project);
            
            response.setMessage(message);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        response.setMessage(message);
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    } 
    
    /**
     * Il metodo updateProject prende l'oggetto RQS proveniente dallo strato "Application", ovvero quello
     * del Controller, copia il contenuto di tale oggetto in un nuovo oggetto Project e lancia,
     * tramite la DaoFactory, i metodi updateProject e updateEmployeesAssociation dello strato "Domain".
     * L'esito dell'operazione viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato)
     * all'interno del quale viene settato un messaggio di fallimento o successo.
     * @param request ProjectEmployeesRQS
     * @return EmptyRES response
     * @author L.Camerlengo
     */
    @Override
    public EmptyRES updateProject(ProjectEmployeesRQS request){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio updateProject");
        EmptyRES response = new EmptyRES();
        ProjectRQS projectRequest = request.getProject();
        int[] employees = request.getEmployees();
        
        Project project = new Project(projectRequest.getId(),projectRequest.getName(),projectRequest.getDescription(),
                                     projectRequest.getStatus(),projectRequest.getBudget(),projectRequest.getCost(),
                                     projectRequest.getProjectManager());
        
        String message = daoFactory.getProjectDao().updateProject(project);        

        if(message.equals("FAIL")) {
            response.setMessage(message);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        //message = daoFactory.getProjectDao().updateEmployeesAssociation(project.getId(), employees);
        
        if (message.equals("FAIL")) {
            response.setMessage(message);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        response.setMessage(message);
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }
        
    /** 
     * Il metodo displayProject prende un oggetto ProjectRQS proveniente dallo strato "Application", 
     * ovvero quello del Controller, contenente solo l'id del progetto da visualizzare.
     * Questo metodo sfrutta i metodi forniti dallo strato "Domain" per effettuare la retrieve 
     * del progetto e la retrieve dell'associazione con i dipendenti.
     * L'esito dell'operazione viene memorizzato in un oggetto ProjectEmployeesRES all'interno del
     * quale viene settato un messaggio di fallimento o successo.
     * @param request ProjectRQS oggetto che incapsula i dati della richiesta 
     * @return ProjectEmployeesRES response
     * @author Lorenzo Svezia, Luca Talocci
     */
    @Override
    public ProjectEmployeesRES displayProject(ProjectRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio displayProject");
        ProjectEmployeesRES response = new ProjectEmployeesRES();
        
        Project project = new Project(request.getId(),request.getName(),request.getDescription(),
                                      request.getStatus(),request.getBudget(),request.getCost(),
                                      request.getProjectManager());       
        String result = daoFactory.getProjectDao().retrieveProject(project);
        
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        int[] employees = null;//daoFactory.getProjectDao().retrieveEmployeesAssociation(project.getId());
        
        if (employees == null) {
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        ProjectRES projectResponse = new ProjectRES(project.getId(),project.getName(),project.getDescription(),
                                                    project.getStatus(),project.getBudget(),project.getCost(),
                                                    project.getProjectManager());
        response.setProject(projectResponse);
        response.setEmployees(employees);
        
        response.setMessage(result);
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }

   /**
     * Il metodo displayPMsEmployees prende l'EmptyRQS proveniente dallo strato "Application"
     * soltanto per convenzione. Inizializza una lista di employees che viene riempita dal 
     * metodo displayPMsEmployees dello strato "Domain" e poi viene inizializzato 
     * l'oggetto PMsEmployeesRES tramite la lista precedentemente riempita e restituito in output.
     * @param request EmptyRQS
     * @return PMsEmployeesRES response
     * @author Lorenzo Svezia
     */
    @Override
    public PMsEmployeesRES displayPMsEmployees(EmptyRQS request){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio displayPMsEmployees");

        List<List<User>> pmsEmployeesList = daoFactory.getProjectDao().displayPMsEmployees();

        PMsEmployeesRES response = new PMsEmployeesRES();
        List<UserRES> employeesListRES = new ArrayList<>();
        List<UserRES> pmsListRES = new ArrayList<>();
        
        if(pmsEmployeesList == null){
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        else {
            response.setMessage("SUCCESS");
            response.setErrorCode("0");
            response.setEsito(true);           
        }

        List<User> employeesList = pmsEmployeesList.get(0);
        List<User> pmsList = pmsEmployeesList.get(1);
        
        for (User u : employeesList){
            UserRES userRes = new UserRES(u.getId(), u.getUsername(), u.getPassword(),
                                          u.getEmail(), u.getName(), u.getSurname(),
                                          u.getSkill(), u.getIsDeactivated());
            employeesListRES.add(userRes);
        }
        for (User u : pmsList){
            UserRES userRes = new UserRES(u.getId(), u.getUsername(), u.getPassword(),
                                          u.getEmail(), u.getName(), u.getSurname(),
                                          u.getSkill(), u.getIsDeactivated());
            pmsListRES.add(userRes);
        }
        
        response.setPmsList(pmsListRES);
        response.setEmployeesList(employeesListRES);
        
        return response;
    }
    
    
    /**
     * Il metodo displayProjects prende l'EmptyRQS proveniente dallo strato "Application"
     * soltanto per convenzione. Inizializza una lista di progetti che viene riempita dal 
     * metodo displayProjects dello strato "Domain" e poi viene inizializzato 
     * l'oggetto FindProjectsRES tramite la lista precedentemente riempita e restituito in output.
     * Infine viene anche settato un messaggio per comunicare l'esito dell'operazione.
     * @param request EmptyRQS
     * @return FindProjectsRES response
     * @author L.Camerlengo
     */
    @Override
    public FindProjectsRES displayProjects(EmptyRQS request){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio findAllProjects");
        List<Project> projectsList=daoFactory.getProjectDao().displayProjects();
        FindProjectsRES response=new FindProjectsRES();
        if(projectsList==null){
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
        }
        else {
            response.setMessage("SUCCESS");
            response.setErrorCode("0");
            response.setEsito(true);           
        }
        List<ProjectRES> projectsRESList=new ArrayList<>();
        for (Project pr : projectsList){
            ProjectRES projectRES= new ProjectRES(pr.getId(),pr.getName(),pr.getDescription(),pr.getStatus(),pr.getBudget(),pr.getCost(),pr.getProjectManager());
            projectsRESList.add(projectRES);
        }
        response.setProjectsList(projectsRESList);
        return response;
    }
    
    /**
     * Il metodo displayProjectForm prende un oggetto ProjectRQS proveniente
     * dallo strato "Application", ovvero quello del Controller, contenente solo
     * l'id di un progetto. Questo metodo sfrutta i metodi forniti dallo strato
     * "Domain" per effettuare la retrieve del progetto, il prelevamento dei
     * dipendenti e dei projectManager presenti nel database e la retrive degli
     * id dei dipendenti che lavorano al progetto passato in ingresso. L'esito
     * delle operazioni viene memorizzato in un oggetto ProjectFormRES
     * all'interno del quale viene settato un messaggio di fallimento o
     * successo.
     *
     * @param request ProjectRQS
     * @return ProjectFormRES response
     * @author L.Camerlengo
     */
    @Override
    public ProjectFormRES displayProjectForm(ProjectRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio displayProjectForm");
        ProjectFormRES response = new ProjectFormRES();
        Project project = new Project();
        project.setId(request.getId());
        String result = daoFactory.getProjectDao().retrieveProject(project);
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        List<List<User>> users = daoFactory.getProjectDao().displayPMsEmployees();
        if (users == null) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        int[] employeesAssociation = daoFactory.getProjectDao().retrieveEmployeesAssociation(project.getId());
        if (employeesAssociation == null) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        ProjectRES projectRes = new ProjectRES(project.getId(), project.getName(), project.getDescription(), project.getStatus(), project.getBudget(), project.getCost(), project.getProjectManager());
        List<User> employees = users.get(0);
        List<User> pms = users.get(1);
        response.setProject(projectRes);
        List<UserRES> employeesRes = new ArrayList<>();
        for (User u : employees) {
            UserRES ur = new UserRES(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(), u.getName(), u.getSurname(), u.getSkill(), u.getIsDeactivated());
            employeesRes.add(ur);
        }
        response.setEmployees(employeesRes);
        List<UserRES> pmsRes = new ArrayList<>();
        for (User u : pms) {
            UserRES ur = new UserRES(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(), u.getName(), u.getSurname(), u.getSkill(), u.getIsDeactivated());
            pmsRes.add(ur);
        }
        response.setPms(pmsRes);
        response.setEmpolyeesAssociation(employeesAssociation);
        return response;
    }

}
