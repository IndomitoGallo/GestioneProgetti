package it.uniroma2.gestioneprogetti.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component ("daoFactory")
@Scope("singleton")
public class DAOFactory implements IDAOFactory {
    
    private final static Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());
    private final static String LAYERLBL = "****DAO FACTORY LAYER**** ";
    
    @Autowired
    IProjectDAO userDao;
    @Autowired
    IProjectDAO projectDao;
    @Autowired
    ITimesheetDAO timesheetDao;

    /**
     * 
     * @return 
     */
    @Override
    public IProjectDAO getUserDao() {
        LOGGER.log(Level.INFO, LAYERLBL + "Recupero UserDao");
        return userDao;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public IProjectDAO getProjectDao() {
        LOGGER.log(Level.INFO, LAYERLBL + "Recupero ProjectDao");
        return projectDao;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public ITimesheetDAO getTimesheetDao() {
        LOGGER.log(Level.INFO, LAYERLBL + "Recupero TimesheetDao");
        return timesheetDao;
    }
    
}
