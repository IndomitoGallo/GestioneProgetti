package it.uniroma2.gestioneprogetti.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * La classe DAOFactory  è una classe che implementa il pattern abstract factory singleton,
 * esso consente di creare famiglie di oggetti correlate tra di loro attraverso
 * l'utilizzo di un interfaccia comune.
 * In questo modo in base al DAO scelto è possibile restituire l'oggetto DAO grazie all'utilizzo
 * della annotazione Autowired. E' inoltre possibile interfacciarsi con DBMS di vario tipo riducendo
 * al minimo le modifiche sul codice. In questo caso l'unica modifica da apportare è lo spostamento
 * dell'annotazione Component nella relativa classe che si intende utilizzare.
 * 
 * @author Gruppo Talocci
 */
@Component("daoFactory")
@Scope("singleton")
public class DAOFactory implements IDAOFactory {

    private final static Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());
    private final static String LAYERLBL = "****DAO FACTORY LAYER**** ";

    @Autowired
    IUserDAO userDao;
    @Autowired
    IProjectDAO projectDao;

    /**
     *Restituisce un oggetto IUserDAO contenente un oggetto concreto UserDAO al suo interno.
     * @return IUserDAO
     */
    @Override
    public IUserDAO getUserDao() {
        LOGGER.log(Level.INFO, LAYERLBL + "Recupero UserDao");
        return userDao;
    }

    /**
     *Restituisce un oggetto IProjectDAO contenente un oggetto concreto ProjectDAO al suo interno.
     * @return IProjectDAO
     */
    @Override
    public IProjectDAO getProjectDao() {
        LOGGER.log(Level.INFO, LAYERLBL + "Recupero ProjectDao");
        return projectDao;
    }

}
