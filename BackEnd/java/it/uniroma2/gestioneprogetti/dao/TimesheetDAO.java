package it.uniroma2.gestioneprogetti.dao;

import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository("timesheetDAO")
public class TimesheetDAO {
    private final static Logger LOGGER = Logger.getLogger(TimesheetDAO.class.getName());
    private final static String LAYERLBL = "****DAO LAYER**** ";
    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "FAIL";
    
}
