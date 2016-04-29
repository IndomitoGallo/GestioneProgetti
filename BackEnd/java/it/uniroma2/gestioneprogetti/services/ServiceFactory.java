package it.uniroma2.gestioneprogetti.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("serviceFactory")
@Scope("singleton")
public class ServiceFactory implements IServiceFactory {
    
    private final static Logger LOGGER = Logger.getLogger(ServiceFactory.class.getName());
    private final static String LAYERLBL = "****SERVICE FACTORY LAYER**** ";
    
    @Autowired
    IUserService userService;
    
    @Autowired
    IProjectService projectService;
    
    @Override
    public IUserService getUserService() {
        LOGGER.log(Level.INFO, LAYERLBL + "Recupero userService");
        return userService;
    }

    @Override
    public IProjectService getProjectService() { 
        LOGGER.log(Level.INFO, LAYERLBL + "Recupero projectsService");
        return projectService;
    }

    
}
