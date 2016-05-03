package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.util.UtilDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;

@Repository("projectDAO")
public class ProjectDAO implements IProjectDAO {
    
    private final static Logger LOGGER = Logger.getLogger(ProjectDAO.class.getName());
    private final static String LAYERLBL = "****DAO LAYER**** ";
    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "FAIL";
    
    //Qui di seguito viene iniettata l'iniezione della dipendenza di UtilDB
    @Autowired
    private UtilDB utilDB;
    
    /**
     * Il metodo displayProjects() sfrutta i metodi forniti dalla classe UtilDB
     * per estrapolare la lista dei progetti dal Database.
     * @return List<Project>
     * @author Luca Talocci
     */
    @Override
    public List<Project> displayProjects() {
        LOGGER.log(Level.INFO, LAYERLBL + "displayProjects");	
        Connection conn=null;
        Statement stmt=null;
        List<Project> projectsList = null;
        try{
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql = "SELECT * FROM project";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utilDB.query(stmt, sql);
            //setto i campi dell'oggetto del dominio con i dati letti dal database
            projectsList = utilDB.resultSetToProjectArray(rs);
       } catch(SQLException e){
            System.err.println("Database Error!");
            e.printStackTrace();
            return projectsList;
       } finally{
            try{
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return projectsList;
            }
        }
        return projectsList;
    }

    /**
     * Il metodo insertProject(Project project) sfrutta i metodi forniti dalla classe UtilDB
     * per inserire i dati di un progetto nel Database.
     * Inoltre, viene prelevato l'id e settato l'oggetto Project passato come parametro,
     * per un uso successivo dell'identificatore nello UserService.
     * @param project Project il progetto da inserire nel DB
     * @return String esito dell'inserimento
     * @author Lorenzo Bernabei
     */
    @Override
    public String insertProject(Project project) {
        LOGGER.log(Level.INFO, LAYERLBL + "Insert project {0}", project.getName());	
        Connection conn = null;	
        Statement stmt = null;	
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //SQL insert
            String sql = "INSERT INTO project VALUES" + 
                         "(NULL, '"  +      //l'id è definito tramite "auto increment"
                         project.getName() + "', '" +
                         project.getDescription() + "', " + 
                         "In Corso', " +       //status settato in automatico a "In Corso"
                         project.getBudget() + ", " + 
                         "0.0, " +      //il costo è inizialmente settato a 0.0
                         project.getProjectManager() + ")";
            utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
            sql = "SELECT id FROM project WHERE name='" + project.getName() + "'";
            ResultSet rs = utilDB.query(stmt, sql);
            rs.next();
            //set del campo id del progetto, verrà usato nella insert delle associazioni con i dipendenti
            project.setId(rs.getInt(1));
            return SUCCESS;
        } catch (SQLException e) {	//catch di un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return FAIL;
        } finally {
            try {
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return FAIL;
            }
        }
    } 

    /**
     * Il metodo updateProject(Project project) sfrutta i metodi forniti dalla classe UtilDB
     * per aggiornare i dati di un progetto nel Database. Prende in input un oggetto Project
     * ed aggiorna il corrispondente progetto nel DB. L'id contenuto nell'ogetto in input
     * serve da riferimento per individuare i dati del DB da modificare. Il resto dei dati
     * contenuti nell'oggetto vengono invece sostituiti a quelli attuali nel DB.
     * @param project Project il progetto da aggiornare
     * @return String esito dell'aggiornamento
     * @author Davide Vitiello
     */
    @Override
    public String updateProject(Project project) {
        LOGGER.log(Level.INFO, LAYERLBL + "Update project {0}", project.getName());
        
        Connection conn = null;	
        Statement stmt = null;	
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //SQL update
            String sql = "UPDATE project SET name='" + project.getName()
			+ ", description='" + project.getDescription() + "'"
			+ ", status='" + project.getStatus() + "'"
			+ ", budget='" + project.getBudget() + "'"
			+ ", project_manager" + project.getProjectManager() 
			+ "' WHERE id=" + project.getId();
            utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
        } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return FAIL;
        } finally {
            try{
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return FAIL;
            }
	}	
        return SUCCESS;
    }

   /**
    * Il metodo deleteProject(Project project) sfrutta i metodi forniti dalla classe UtilDB
    * per prendere in ingresso un oggetto Project
    * ed eliminare il corrispondente progetto dal DB.
    * L'id contenuto nell'oggetto in ingresso viene utilizzato per individuare le
    * righe del DB che verranno eliminate: tutte quelle che nel campo 'id', nella tabella
    * progetti, hanno lo stesso numero intero presente nel campo 'id' dell'oggetto in ingresso.
    * @param project Project il progetto da eliminare
    * @return String esito della cancellazione
    * @author Davide Vitiello
    */
    @Override
    public String deleteProject(Project project) {
        LOGGER.log(Level.INFO, LAYERLBL + "Delete project {0}", project.getName());
        
        Connection conn = null;	
        Statement stmt = null;	
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //SQL delete
            String sql = "DELETE FROM project WHERE id=" + project.getId();
            utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
        } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return FAIL;
        } finally {
            try{
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return FAIL;
            }
	}	
        return SUCCESS;
    }

    /**
     * Effettua l'operazione di retrieve, ovvero il recupero dei dati nel database del Project
     * passato come argomento settando tutti i parametri di esso.
     * Restituisce SUCCESS se il recupero e il settaggio dei dati e' andato a buon fine, FAIL altrimenti.
     * @param p Project
     * @return String esito del recupero dei dati
     * @author L.Camerlengo
     */ 
    @Override
    public String retrieveProject(Project p){
        LOGGER.log(Level.INFO, LAYERLBL + "retrieve Project");	
        Connection conn = null;
        Statement stm = null;
        try {
            conn = utilDB.createConnection();
            stm = conn.createStatement();
            String query = "select * from project where id=" + p.getId();
            ResultSet rs = utilDB.query(stm, query);
            if (!rs.next()) {
                return FAIL;
            }
            p.setName(rs.getString(2));
            p.setDescription(rs.getString(3));
            p.setStatus(rs.getString(4));
            p.setBudget(rs.getDouble(5));
            p.setCost(rs.getDouble(6));
            p.setProjectManager(rs.getInt(7));

        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return FAIL;
        } finally {
            try {
                if (stm != null) {
                    utilDB.closeStatement(stm);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Database Error!");
                e.printStackTrace();
                return FAIL;
            }
        }
        return SUCCESS;
    }
    
    /**
     * Il metodo displayPMProjects(int idPM) sfrutta i metodi forniti dalla classe UtilDB
     * per estrapolare la lista dei progetti di un ProjectManager dal Database.
     * @param idPM
     * @return List<Project>
     */
    @Override
    public List<Project> displayPMProjects(int idPM) {
        LOGGER.log(Level.INFO, LAYERLBL + "displayPMProjects");	
        Connection conn=null;
        Statement stmt=null;
        List<Project> projectsList = null;
        try{
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql="SELECT * FROM project WHERE project_manager=" + idPM;
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utilDB.query(stmt, sql);
            //setto i campi dell'oggetto del dominio con i dati letti dal database
            projectsList = utilDB.resultSetToProjectArray(rs);
       } catch(SQLException e){
            System.err.println("Database Error!");
            e.printStackTrace();
            return projectsList;
       } finally{
            try{
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return projectsList;
            }
        }
        return projectsList;
    }
    
}
