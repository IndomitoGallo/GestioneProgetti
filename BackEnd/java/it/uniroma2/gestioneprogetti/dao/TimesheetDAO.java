package it.uniroma2.gestioneprogetti.dao;

import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 * La classe TimesheetDAO sfrutta i metodi della classe UtilDB per effettuare
 * operazioni sul database che riguardano la gestione del Timesheet. In questo modo tutte le
 * operazioni sul database riguardanti il Timesheet, vengono incapsulate esclusivamente all'interno di questa
 * classe.
 * @author Gruppo Talocci
 */
@Repository("timesheetDAO")
public class TimesheetDAO {
    private final static Logger LOGGER = Logger.getLogger(TimesheetDAO.class.getName());
    private final static String LAYERLBL = "****DAO LAYER**** ";
    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "FAIL";
    
}
