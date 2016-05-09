/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.gestioneprogetti.controller;

import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.services.IServiceFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author L.Camerlengo
 */
@RestController
public class ProjectController {
    
    @Autowired
    private IServiceFactory serviceFactory;
    private final static String LAYERLBL = "****REST PROJECT CONTROLLER LAYER**** ";
    private final static Logger LOGGER = Logger.getLogger(ProjectController.class.getName());
    
    /**
     * 
     * Il metodo intercetta le richieste del Front-End che richiedono di cancellare il progetto che
     * ha associato l'id passato in ingresso.
     * Viene quindi creato l'oggetto ProjectRQS che incapsula la richiesta e prima di chiamare il servizio del livello
     * sottostante viene verificato se l'utente ha una sessione attiva. In caso di esito negativo viene restituito
     * l' http status UNAUTHORIZED altrimenti viene memorizzata la response in un EmptyRES. Se l'esito contenuto nella response
     * e' positivo viene restituito l'http status OK, altrimenti viene restituito lo status SERVICE_UNAVAILABLE. 
     * @param id int
     * @param session String
     * @return ResponseEntity response
     * @author L.Camerlengo
     */
    @RequestMapping(value="/projects/{id}",method=RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable("id")int id,@PathVariable("session")String session){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a rest project controller method deleteProject");
        if(SessionController.verify(session)==false){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        ProjectRQS request= new ProjectRQS();
        request.setId(id);
        EmptyRES response=serviceFactory.getProjectService().deleteProject(request);
        if(response.isEsito()){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }      
    }
}
