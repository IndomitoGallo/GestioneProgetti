package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import it.uniroma2.gestioneprogetti.util.UtilDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * La classe ProjectDAO sfrutta i metodi della classe UtilDB per effettuare
 * operazioni sul database che riguardano i progetti. In questo modo tutte le
 * operazioni sul database riguardanti i progetti vengono incapsulate esclusivamente all'interno di questa
 * classe.
 * @author Gruppo Talocci
 */
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
     *
     * @return List<Project>
     * @author Luca Talocci
     */
    @Override
    public List<Project> displayProjects() {
        LOGGER.log(Level.INFO, LAYERLBL + "displayProjects");
        Connection conn = null;
        Statement stmt = null;
        List<Project> projectsList = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql = "SELECT * FROM project";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utilDB.query(stmt, sql);
            //setto i campi dell'oggetto del dominio con i dati letti dal database
            projectsList = utilDB.resultSetToProjectArray(rs);
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return projectsList;
        } finally {
            try {
                if (stmt != null) {
                    utilDB.closeStatement(stmt);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return projectsList;
            }
        }
        return projectsList;
    }

    /**
     * Il metodo insertProject(Project project) sfrutta i metodi forniti dalla
     * classe UtilDB per inserire i dati di un progetto nel Database. Inoltre,
     * viene prelevato l'id e settato l'oggetto Project passato come parametro,
     * per un uso successivo dell'identificatore nello UserService.
     *
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
            String sql = "INSERT INTO project VALUES"
                    + "(NULL, '"
                    + //l'id è definito tramite "auto increment"
                    project.getName() + "', '"
                    + project.getDescription() + "', "
                    + "'in corso', "
                    + //status settato in automatico a "In Corso"
                    project.getBudget() + ", "
                    + "0.0, "
                    + //il costo è inizialmente settato a 0.0
                    project.getProjectManager() + ")";
            utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
            sql = "SELECT id FROM project WHERE name='" + project.getName() + "'";
            ResultSet rs = utilDB.query(stmt, sql);
            rs.next();
            //set del campo id del progetto, verrà usato nella insert delle associazioni con i dipendenti
            project.setId(rs.getInt(1));
        } catch (SQLException e) {	//catch di un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return FAIL;
        } finally {
            try {
                if (stmt != null) {
                    utilDB.closeStatement(stmt);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return FAIL;
            }
        }
        return SUCCESS;
    }

    /**
     * Il metodo updateProject(Project project) sfrutta i metodi forniti dalla
     * classe UtilDB per aggiornare i dati di un progetto nel Database. Prende
     * in input un oggetto Project ed aggiorna il corrispondente progetto nel
     * DB. L'id contenuto nell'ogetto in input serve da riferimento per
     * individuare i dati del DB da modificare. Il resto dei dati contenuti
     * nell'oggetto vengono invece sostituiti a quelli attuali nel DB.
     *
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
                    + "', description='" + project.getDescription() + "'"
                    + ", status='" + project.getStatus() + "'"
                    + ", budget=" + project.getBudget() 
                    + ", project_manager=" + project.getProjectManager()
                    + " WHERE id=" + project.getId();
            utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
        } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return FAIL;
        } finally {
            try {
                if (stmt != null) {
                    utilDB.closeStatement(stmt);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return FAIL;
            }
        }
        return SUCCESS;
    }

    /**
     * Il metodo deleteProject(Project project) sfrutta i metodi forniti dalla
     * classe UtilDB per prendere in ingresso un oggetto Project ed eliminare il
     * corrispondente progetto dal DB. L'id contenuto nell'oggetto in ingresso
     * viene utilizzato per individuare le righe del DB che verranno eliminate:
     * tutte quelle che nel campo 'id', nella tabella progetti, hanno lo stesso
     * numero intero presente nel campo 'id' dell'oggetto in ingresso.
     *
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
            try {
                if (stmt != null) {
                    utilDB.closeStatement(stmt);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return FAIL;
            }
        }
        return SUCCESS;
    }

    /**
     * Effettua l'operazione di retrieve, ovvero il recupero dei dati nel
     * database del Project passato come argomento settando tutti i parametri di
     * esso. Restituisce SUCCESS se il recupero e il settaggio dei dati e'
     * andato a buon fine, FAIL altrimenti.
     *
     * @param p Project
     * @return String esito del recupero dei dati
     * @author L.Camerlengo
     */
    @Override
    public String retrieveProject(Project p) {
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
     * Il metodo displayPMProjects(int idPM) sfrutta i metodi forniti dalla
     * classe UtilDB per estrapolare la lista dei progetti di un ProjectManager
     * dal Database.
     *
     * @param idPM l'id del Project Manager
     * @return List la lista con i progetti
     * @author Luca Talocci
     */
    @Override
    public List<Project> displayPMProjects(int idPM) {
        LOGGER.log(Level.INFO, LAYERLBL + "displayPMProjects");
        Connection conn = null;
        Statement stmt = null;
        List<Project> projectsList = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql = "SELECT * FROM project WHERE project_manager=" + idPM;
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utilDB.query(stmt, sql);
            //setto i campi dell'oggetto del dominio con i dati letti dal database
            projectsList = utilDB.resultSetToProjectArray(rs);
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return projectsList;
        } finally {
            try {
                if (stmt != null) {
                    utilDB.closeStatement(stmt);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return projectsList;
            }
        }
        return projectsList;
    }

    /**
     * Il metodo displayPMsEmployees() seleziona i dipendenti e i projectManager
     * presenti nel database, in modo tale che l'utente Controller possa
     * visualizzarli all'interno di un form. Restituisce una List<List<User>>
     * inizializzata a null nel caso in cui l'operazione non va a buon fine,
     * altrimenti restituisce una List<List<User>> contente al suo interno due
     * List<User>, dove la prima lista contiene i dipendenti e la seconda
     * contiene i projectManager.
     * @return List<List<User>> dipendenti e projectManager
     * @author L.Camerlengo
     */
    @Override
    public List<List<User>> displayPMsEmployees() {
        LOGGER.log(Level.INFO, LAYERLBL + "displayPMsEmployees");
        Connection conn = null;
        Statement stm = null;
        List<List<User>> users = null;
        try {
            conn = utilDB.createConnection();
            stm = utilDB.createStatement(conn);
            String query1 = "select u.username,u.id,u.name,u.surname from profileUser p, user u where p.profile=3 and p.user=u.id"
                    + " and u.isDeactivated=false";
            String query2 = "select u.username,u.id,u.name,u.surname from profileUser p, user u where p.profile=4 and p.user=u.id"
                    + " and u.isDeactivated=false";
            ResultSet rs1 = utilDB.query(stm, query1);
            List<User> employees = new ArrayList<>();
            while (rs1.next()) {
                String username = rs1.getString(1);
                int id = rs1.getInt(2);
                String name = rs1.getString(3);
                String surname = rs1.getString(4);
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setName(name);
                user.setSurname(surname);
                employees.add(user);
            }
            ResultSet rs2 = utilDB.query(stm, query2);
            List<User> pms = new ArrayList<>();
            while (rs2.next()) {
                String username = rs2.getString(1);
                int id = rs2.getInt(2);
                String name = rs2.getString(3);
                String surname = rs2.getString(4);
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setName(name);
                user.setSurname(surname);
                pms.add(user);
            }
            users = new ArrayList<>();
            users.add(employees);
            users.add(pms);
        } catch (SQLException e) {
            System.err.println("Close Resource Error!");
            e.printStackTrace();
            return users;
        } finally {
            try {
                if (stm != null) {
                    utilDB.closeStatement(stm);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return users;
            }
        }
        return users;
    }

    /**
     * Il metodo updateEmployeesAssociation effettua un aggiornamento nel
     * database delle associazioni dei dipendenti che lavorano ad un determinato
     * progetto. In particolare per il progetto passato in ingresso vengono
     * mantenute nel database soltanto le associazioni dei dipendenti il cui id
     * è contenuto all' interno dell'array passato in ingresso. Restituisce
     * SUCCESS nel caso in cui l'operazione ha esito positivo, FAIL altrimenti.
     * @param idProject int
     * @param employees int[]
     * @return String esito
     * @author L.Camerlengo
     */
    @Override
    public String updateEmployeesAssociation(int idProject, int[] employees) {
        LOGGER.log(Level.INFO, LAYERLBL + "updateEmployeesAssociation");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = utilDB.createConnection();
            stm = utilDB.createStatement(conn);
            String query;
            ResultSet res;
            for (int id : employees) {
                query = "select p.user,p.project from projectUser p where p.user=" + id + " and p.project=" + idProject;
                res = utilDB.query(stm, query);
                if (!res.next()) {
                    String insert = "insert into projectUser values(" + id + "," + idProject + ",0)";
                    if (utilDB.manipulate(stm, insert) != 1) {
                        return FAIL;
                    }
                }
            }
            query = "select p.user from projectUser p where p.project=" + idProject;
            res = utilDB.query(stm, query);
            
            List<Integer> emp = new ArrayList<>();
            for(int i = 0; i < employees.length; i++) {
                emp.add(employees[i]);
            }   
            
            while (res.next()) {
                if (!emp.contains(res.getInt(1))) {
                    query = "delete from projectUser p where p.user=" + res.getInt(1);
                    if (utilDB.manipulate(stm, query) != 1) {
                        return FAIL;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Close Resource Error!");
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
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return FAIL;
            }
        }
        return SUCCESS;
    }

    /**
     * Il metodo insertEmployeesAssociation associa i dipendenti ad un determinato progetto.
     * Restituisce SUCCESS nel caso in cui l'operazione ha esito positivo, FAIL altrimenti.
     * @param idProject int
     * @param employees int[]
     * @return String esito
     * @author Lorenzo Svezia
     */
    @Override
    public String insertEmployeesAssociation(int idProject, int[] employees) {
        LOGGER.log(Level.INFO, LAYERLBL + "insertEmployeesAssociation");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = utilDB.createConnection();
            stm = utilDB.createStatement(conn);

            for (int id : employees) {
                String insert = "INSERT INTO projectUser VALUES(" + id + "," + idProject + ",0)";
		utilDB.manipulate(stm, insert);
            }
        } catch (SQLException e) {
            System.err.println("Close Resource Error!");
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
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return FAIL;
            }
        }

        return SUCCESS;
    }

    /**
     * Il metodo retrieveEmployeesAssociation(int idProject) sfrutta i metodi
     * forniti dalla classe UtilDB per prelevare dal Database le associazioni di
     * un progetto con gli utenti che vi lavorano. Il valore di ritorno è
     * l'array contentente gli id degli utenti,o null in caso di eccezioni.
     *
     * @param idProject int id del progetto
     * @return int[] array con gli id dei utenti
     * @author Davide Vitiello
     */
    @Override
    public int[] retrieveEmployeesAssociation(int idProject) {
        LOGGER.log(Level.INFO, LAYERLBL + "Retrieve project-employees association");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //Query SQL per sapere quanti impiegati sono associati al progetto
            String sql1 = "SELECT COUNT(*) FROM projectUser WHERE project=" + idProject;
            ResultSet rs1 = utilDB.query(stmt, sql1);
            //Riempio l'array passato in ingresso con gli id degli impiegati
            rs1.next();
            int[] employees = new int[rs1.getInt(1)];
            //Query SQL per reperire tutti gli utenti associati al progetti
            String sql2 = "SELECT user FROM projectUser WHERE project=" + idProject;
            ResultSet rs2 = utilDB.query(stmt, sql2);

            int i = 0;
            while (rs2.next()) {
                employees[i] = rs2.getInt(1);
                i++;
            }
            return employees;
        } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stmt != null) {
                    utilDB.closeStatement(stmt);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return null;
            }
        }
    }
    
    /**
     * Il metodo retrieveEmployeesAndHours(int idProject)
     * seleziona gli utenti e le rispettive ore dedicate ad un progetti, in modo
     * tale che l'utente Controller possa visualizzarli. Restituisce una
     * List<List<Integer>> inizializzata a null nel caso in cui l'operazione non
     * vada a buon fine, oppure, in caso contrario, una List<List<Integer>>
     * contente al suo interno due List<Integer>, dove la prima lista contiene i
     * dipendenti e la seconda contiene le ore dedicate al progetto.
     *
     * @param idProject int id del progetto in questione
     * @return List<List<Object> dipendenti e loro ore dedicate al progetto
     * @author Davide Vitiello
     */
    @Override
    public List<List<Object>> retrieveEmployeesAndHours(int idProject) {
        LOGGER.log(Level.INFO, LAYERLBL + "retrieveEmployeesAndHours");
        Connection conn = null;
        Statement stm = null;
        List<List<Object>> employeesAndHours = null;
        try {
            conn = utilDB.createConnection();
            stm = utilDB.createStatement(conn);
            String sql1 = "select total_hours from projectUser where project=" 
                            + idProject 
                            + " order by user asc";
            ResultSet rs1 = utilDB.query(stm, sql1);
            List<Object> hours = new ArrayList<>();
            while (rs1.next()) {
                Object totalHours = rs1.getInt(1);
                hours.add(totalHours);
            }
            List<Object> employees = new ArrayList<>();
            String sql2 = "select u.* from user u, projectUser p where u.id=p.user and p.project=" 
                            + idProject 
                            + " order by u.id asc";
            ResultSet rs2 = utilDB.query(stm, sql2);
            while(rs2.next()) {
                Object u = new User(rs2.getInt(1), rs2.getString(2), rs2.getString(3), 
                                  rs2.getString(4), rs2.getString(5), rs2.getString(6), 
                                  rs2.getString(7), rs2.getBoolean(8));
                employees.add(u);
            }
            employeesAndHours = new ArrayList<>();
            employeesAndHours.add(employees);
            employeesAndHours.add(hours);
        } catch (SQLException e) {
            System.err.println("Close Resource Error!");
            e.printStackTrace();
            return employeesAndHours;
        } finally {
            try {
                if (stm != null) {
                    utilDB.closeStatement(stm);
                }
                if (conn != null) {
                    utilDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return employeesAndHours;
            }
        }
        return employeesAndHours;
    }
    
    /**
     * Effettua l'operazione di retrieve, ovvero il recupero del nome
     * e del cognome nel database del PM passato come argomento.
     * Restituisce i dati se il recupero e' andato a buon fine, FAIL altrimenti.
     *
     * @param idPM id Project Manager
     * @return String esito del recupero dei dati
     * @author Lorenzo Svezia
     */
    @Override
    public String retrievePMName(int idPM) {
        LOGGER.log(Level.INFO, LAYERLBL + "retrieve PM name");

        Connection conn = null;
        Statement stm = null;
        String pmName = "";

        try {
            conn = utilDB.createConnection();
            stm = conn.createStatement();

            String query = "SELECT name, surname FROM user WHERE id="+ idPM;

            ResultSet rs = utilDB.query(stm, query);

            if (!rs.next()) {
                return FAIL;
            }
            
            pmName = rs.getString(1) + " " + rs.getString(2);

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
        
        return pmName;
    }
    
}
