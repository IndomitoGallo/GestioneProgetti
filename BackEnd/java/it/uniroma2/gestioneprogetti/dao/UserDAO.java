package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.User;
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

/**
 * La classe UserDAO sfrutta i metodi della classe UtilDB per effettuare
 * operazioni sul database che riguardano i progetti. In questo modo tutte le
 * operazioni sul database riguardanti gli utenti vengono incapsulate esclusivamente all'interno di questa
 * classe.
 * @author Gruppo Talocci
 */
@Repository("userDAO")
public class UserDAO implements IUserDAO {

    private final static Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private final static String LAYERLBL = "****DAO LAYER**** ";
    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "FAIL";

    //Qui di seguito viene iniettata l'iniezione della dipendenza di UtilDB
    @Autowired
    private UtilDB utilDB;

    /**
     * Il metodo displayUsers() sfrutta i metodi forniti dalla classe UtilDB per
     * estrapolare la lista degli utenti dal Database. Notare che per "utenti"
     * si intendono i profili diversi da "Controller" e "Amministratore".
     *
     * @return List<User>
     * @author Luca Talocci
     */
    @Override
    public List<User> displayUsers() {
        LOGGER.log(Level.INFO, LAYERLBL + "displayUsers");
        Connection conn = null;
        Statement stmt = null;
        List<User> usersList = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql = "SELECT * FROM user where id > 2";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utilDB.query(stmt, sql);
            //setto i campi dell'oggetto del dominio con i dati letti dal database
            usersList = utilDB.resultSetToUserArray(rs);
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return usersList;
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
                return usersList;
            }
        }
        return usersList;
    }

    /**
     * Il metodo insertUser(User u) sfrutta i metodi forniti dalla classe UtilDB
     * per inserire i dati di un utente nel Database.     *
     * @param user User
     * @return String esito dell'inserimento
     * @author Lorenzo Svezia
     */
    @Override
    public String insertUser(User user) {
        LOGGER.log(Level.INFO, LAYERLBL + "insert user {0}", user.getUsername());

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //inserimento SQL
            String sql = "INSERT INTO user VALUES(NULL, '" + user.getUsername() + "', '"
                    + user.getPassword() + "', '"
                    + user.getEmail() + "', '"
                    + user.getName() + "', '"
                    + user.getSurname() + "', '"
                    + user.getSkill() + "', "
                    + '0' + ", " //l'utente che viene creato all'inizio è attivo (non è 'deattivato')
                    + "NULL" // La seniority non è assegnata dall'amministratore
                    + ")";
            utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
            sql = "SELECT id FROM user WHERE username='" + user.getUsername() + "'";
            ResultSet rs = utilDB.query(stmt, sql);
            rs.next();
            user.setId(rs.getInt(1)); //set del campo id dell'utente 
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
            } catch (SQLException y) {
                System.err.println("Closing Resources Error!");
                y.printStackTrace();
                return FAIL;
            }
        }
        return SUCCESS;
    }

    /**
     * Il metodo updateUser(User user) sfrutta i metodi forniti dalla classe
     * UtilDB per modificare i dati di un utente nel Database.
     *
     * @param user User utente da modificare nel DB
     * @return String esito della modifica
     * @author Luca Talocci
     */
    @Override
    public String updateUser(User user) {
        LOGGER.log(Level.INFO, LAYERLBL + "Update user {0}", user.getUsername());

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection(); //connection to DB
            stmt = conn.createStatement();  //creazione dello Statement
            int userId = user.getId();
            //SQL insert
            String sql = "UPDATE user SET name='" + user.getName() + "', "
                    + "surname='" + user.getSurname() + "', "
                    + "username='" + user.getUsername() + "', "
                    + "email='" + user.getEmail() + "', "
                    + "password='" + user.getPassword() + "', "
                    + "skill='" + user.getSkill() + "', "
                    + "isDeactivated=" + user.getIsDeactivated() + " "
                    + "WHERE id=" + userId;
            utilDB.manipulate(stmt, sql);
        } catch (SQLException e) {  //il metodo intercetta un'eccezione proveniente dal DB         
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
     * Il metodo deleteUser(User u) sfrutta i metodi forniti dalla classe UtilDB
     * per eliminare i dati di un utente specifico nel Database.
     *
     * @param user User
     * @return String esito della cancellazione
     * @author Lorenzo Svezia
     */
    @Override
    public String deleteUser(User user) {
        LOGGER.log(Level.INFO, LAYERLBL + "delete user");

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //SQL delete
            String sql = "DELETE FROM user WHERE id=" + user.getId();
            //esecuzione del comando SQL
            if (utilDB.manipulate(stmt, sql) == 0) {
                //se la query non ha interessato alcun record del DB, viene restituita una stringa di errore
                return FAIL;
            }
        } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
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
     * Effettua l'operazione di retrieve, ovvero il recupero dei dati
     * dell'utente passato come argomento settando tutti i parametri di esso.
     * Restituisce SUCCESS se il recupero e il settaggio dei dati e' andato a
     * buon fine, FAIL altrimenti.
     *
     * @param u User
     * @return String esito del recupero dei dati
     * @autor Francesco Gaudenzi
     */
    @Override
    public String retrieveUser(User u) {
        LOGGER.log(Level.INFO, LAYERLBL + "retrieve user");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = utilDB.createConnection();
            stm = conn.createStatement();
            String query = "select * from user where id=" + u.getId();
            ResultSet rs = utilDB.query(stm, query);
            if (!rs.next()) {
                return FAIL;
            }
            u.setUsername(rs.getString(2));
            u.setPassword(rs.getString(3));
            u.setEmail(rs.getString(4));
            u.setName(rs.getString(5));
            u.setSurname(rs.getString(6));
            u.setSkill(rs.getString(7));
            u.setIsDeactivated(rs.getBoolean(8));

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
     * Il metodo verifyLoginData(String user, String pwd, int profile) verifica
     * che i dati di Login inseriti dall'utente corrispondano ai dati immessi in
     * fase di registrazione dall'utente che deve effettuare l'accesso. Il
     * valore di ritorno è l'id dell'utente se i dati di login sono corretti, 0
     * altrimenti. In caso di eccezioni viene restituito -1.
     *
     * @param user String lo username dell'utente
     * @param pwd String la password dell'utente
     * @param profile int il profilo con cui l'utente vuole accedere
     * @return int esito della verifica
     * @author Luca Talocci
     */
    @Override
    public int verifyLoginData(String user, String pwd, int profile) {
        LOGGER.log(Level.INFO, LAYERLBL + "Verify user login data");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection(); //connessione al DB
            stmt = conn.createStatement();  //creazione dello Statement
            int userId; //id dell'utente che vuole loggarsi
            //SQL select
            String sql1 = "SELECT id FROM user WHERE username='" + user + "' AND "
                    + "password='" + pwd + "' AND isDeactivated=0";
            //memorizzazione del risultato delle query in un ResultSet
            ResultSet rs1 = utilDB.query(stmt, sql1);
            if (!rs1.next()) {
                return 0; //username o password passati in ingresso al metodo inesistenti
            }            //Verifico che l'utente sia associato al profilo richiesto
            else userId = rs1.getInt(1);
            String sql2 = "SELECT * FROM profileUser WHERE user=" + rs1.getInt(1) + " AND profile=" + profile;
            ResultSet rs2 = utilDB.query(stmt, sql2);
            if (!rs2.next()) {
                return 0; //il profilo richiesto non è associato all'utente
            }
            //username e password passati in ingresso al metodo sono presenti nel database 
            //e l'utente è associato al profilo richiesto
            return userId;
        } catch (SQLException e) {      //catch di un'eccezione proveniente dal DB         
            System.err.println("Database Error!");
            e.printStackTrace();
            return -1;
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
                return -1;
            }
        }
    }

    /**
     * Il metodo verifyCreationData(String user, String mail) verifica che i
     * dati di registrazione inseriti dall'utente, in particolare username e
     * email, non siano già presenti nel database. Il valore di ritorno è "true"
     * se i dati di registrazione sono corretti, "false" altrimenti. In caso di
     * eccezioni viene restituito fail.
     *
     * @param user String lo username dell'utente
     * @param mail String l'e-mail dell'utente
     * @return String esito della verifica
     * @author Lorenzo Bernabei
     */
    @Override
    public String verifyCreationData(String user, String mail) {
        LOGGER.log(Level.INFO, LAYERLBL + " Verify project creation data");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //SQL select
            String sql1 = "SELECT * FROM user WHERE username='" + user + "'";
            String sql2 = "SELECT * FROM user WHERE email='" + mail + "'";
            //memorizzazione del risultato delle query in due ResultSet
            ResultSet rs1 = utilDB.query(stmt, sql1);
            if (rs1.next()) {
                return "false";	//username passato in ingresso al metodo già presente nel database
            }
            ResultSet rs2 = utilDB.query(stmt, sql2);
            if (rs2.next()) {
                return "false";	//mail passata in ingresso al metodo già presente nel database
            }
            return "true"; //username e mail passati in ingresso al metodo non sono presenti nel database
        } catch (SQLException e) {      //catch di un'eccezione proveniente dal DB	    	 
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
    }

    /**
     * Il metodo verifyUpdateData(int idUser, String user, String mail) verifica
     * che i dati di aggiornamento del profilo inseriti dall'utente, in
     * particolare username e email, non siano già presenti nel database. Il
     * valore di ritorno è true se i dati inseriti sono corretti, false
     * altrimenti. In caso di eccezioni viene restituito fail.
     *
     * @param idUser int l'id dell'utente, per poter effettuare la verifica su
     * tutti gli altri utenti
     * @param user String lo username dell'utente
     * @param mail String l'e-mail dell'utente
     * @return boolean esito della verifica
     * @author Francesco Gaudenzi
     */
    @Override
    public String verifyUpdateData(int idUser, String user, String mail) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();   //connection to DB
            stmt = conn.createStatement();    //creazione dello Statement
            //SQL select
            String sql1 = "SELECT * FROM user WHERE username='" + user + "' AND id<>" + idUser;
            String sql2 = "SELECT * FROM user WHERE email='" + mail + "' AND id<>" + idUser;
            //memorizzazione del risultato delle query in un ResultSet
            ResultSet rs1 = utilDB.query(stmt, sql1);
            if (rs1.next()) {
                return "false"; //username passato in ingresso al metodo già presente nel database
            }
            ResultSet rs2 = utilDB.query(stmt, sql2);
            if (rs2.next()) {
                return "false"; //mail passata in ingresso al metodo già presente nel database
            }
            return "true"; //username e mail passati in ingresso al metodo non sono presenti nel database
        } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB 
            System.err.println("Database Error!");
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
                return FAIL;
            }
        }
    }

    /**
     * Effettua la cancellazione logica dell'utente che ha associato l'id
     * passato in ingresso, ovvero l'utente non viene cancellato, ma viene
     * settato nel database un flag che lo disattiva. Restituisce SUCCESS se la
     * cancellazione è andata a buon fine, FAIL altrimenti.
     *
     * @param idUser int
     * @return String esito della cancellazione logica dell'utente
     * @author L.Camerlengo
     */
    @Override
    public String deactivateUser(int idUser) {
        LOGGER.log(Level.INFO, LAYERLBL + "Deactivate User");
        Connection conn = null;
        Statement stm = null;
        try {
            conn = utilDB.createConnection();
            stm = conn.createStatement();
            String update = "UPDATE user set isDeactivated=1 where id=" + idUser;
            int res = utilDB.manipulate(stm, update);
            if (res != 1) {
                return FAIL;
            }
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
     * Il metodo insertProfilesAssociation(int idUser, int[] profiles) sfrutta i
     * metodi forniti dalla classe UtilDB per inserire nel Database le
     * associazioni di un utente con i profili. Il valore di ritorno è "SUCCESS"
     * se l'inserimento è andato a buon fine o "FAIL" in caso di eccezioni.
     *
     * @param idUser int
     * @param profiles int[] array con gli id dei profili
     * @return String esito dell'inserimento delle associazioni
     * @author Lorenzo Bernabei
     */
    @Override
    public String insertProfilesAssociation(int idUser, int[] profiles) {
        LOGGER.log(Level.INFO, LAYERLBL + " Insert user-profiles association");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connection to DB
            stmt = conn.createStatement();	//creazione dello Statement
            for (int idPro : profiles) {	//per ognuna dei profili associati all'utente
                //SQL insert
                String sql = "INSERT INTO profileUser VALUES(" + idUser + ", " + idPro + ")";
                utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
            }
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
     * Il metodo retrieveProfilesAssociation(int idUser) sfrutta i metodi
     * forniti dalla classe UtilDB per prelevare dal Database le associazioni di
     * un utente con i profili. Il valore di ritorno è l'array degli id dei
     * profili se l'inserimento è andato a buon fine o null in caso di
     * eccezioni.
     *
     * @param idUser int
     * @return int[] array con gli id dei profili
     * @author Luca Talocci
     */
    @Override
    public int[] retrieveProfilesAssociation(int idUser) {
        LOGGER.log(Level.INFO, LAYERLBL + "Retrieve user-profiles association");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connection to DB
            stmt = conn.createStatement();	//creazione dello Statement
            //Query SQL per sapere quanti profili sono associati all'utente
            String sql1 = "SELECT COUNT(*) FROM profileUser WHERE user=" + idUser;
            ResultSet rs1 = utilDB.query(stmt, sql1);
            //Riempio l'array passato in ingresso con i profili presi dal database
            rs1.next();
            int[] profiles = new int[rs1.getInt(1)];
            int i = 0;
            //Query SQL
            String sql2 = "SELECT profile FROM profileUser WHERE user=" + idUser;
            ResultSet rs2 = utilDB.query(stmt, sql2);
            while (rs2.next()) {
                profiles[i] = rs2.getInt(1);
                i++;
            }
            return profiles;
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
     * Il metodo updateProfilesAssociation(int idUser, int[] profiles) sfrutta i
     * metodi forniti dalla classe UtilDB per aggiornare nel Database le
     * associazioni di un utente con i profili. L'aggiornamento viene effettuato
     * cancellando tutte le occorrenze di profileUser di un determinato utente e
     * inserendo quelle nuove. Il valore di ritorno è "SUCCESS" se
     * l'aggiornamento è andato a buon fine o "FAIL" in caso di eccezioni.
     *
     * @param idUser int
     * @param profiles int[] array con gli id dei profili
     * @return String esito dell'aggiornamento delle associazioni
     * @author Francesco Gaudenzi
     */
    @Override
    public String updateProfilesAssociation(int idUser, int[] profiles) {
        LOGGER.log(Level.INFO, LAYERLBL + " Update user-profiles association");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connection to DB
            stmt = conn.createStatement();	//creazione dello Statement

            String sqlDelete = "DELETE FROM profileUser WHERE user=" + idUser; // Cancello tutte le occorrenze
            utilDB.manipulate(stmt, sqlDelete);	//esecuzione del comando SQL

            for (int idPro : profiles) {	//per ognuna dei profili associati all'utente
                //SQL insert
                String sqlInsert = "INSERT INTO profileUser VALUES(" + idUser + ", " + idPro + ")";
                utilDB.manipulate(stmt, sqlInsert);	//esecuzione del comando SQL
            }

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
     * Il metodo verifyProfiles(int idUser) sfrutta i metodi forniti dalla
     * classe UtilDB per effettuare una codifica in stringa dei profili
     * associati all'utente identificato da idUser passato come parametro. In
     * particolare viene restituito: - "DIP" se l'utente è un Dipendente; - "PM"
     * se l'utente è un Project Manager; - "DIPPM" se l'utente è sia Dipendente
     * che Project Manager. In caso di eccezioni viene restituito "FAIL".
     *
     * @param idUser int l'id dell'utente su cui effettuare la verifica
     * @return String codice che rappresenta il risultato della verifica
     * @author Lorenzo Bernabei
     */
    @Override
    public String verifyProfiles(int idUser) {
        LOGGER.log(Level.INFO, LAYERLBL + " Verify user profiles");
        Connection conn = null;
        Statement stmt = null;
        String result = "";
        try {
            conn = utilDB.createConnection();	//connection to DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql = "SELECT profile.name FROM profileUser, profile "
                    + "WHERE profileUser.profile = profile.id AND profileUser.user=" + idUser
                    + " ORDER BY profile.name";
            ResultSet rs = utilDB.query(stmt, sql);	//esecuzione del comando SQL
            while (rs.next()) {
                if (rs.getString(1).equals("Dipendente")) {
                    result = result + "DIP";
                    LOGGER.log(Level.INFO, LAYERLBL + " Hai scelto un dipendente --> " + result);
                }
                if (rs.getString(1).equals("Project Manager")) {
                    result = result + "PM";
                    LOGGER.log(Level.INFO, LAYERLBL + " Hai scelto un pm --> " + result);
                }
            }
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

        return result;
    }

    /**
     * Il metodo verifyProjectsStatus(int idPM) sfrutta i metodi forniti dalla
     * classe UtilDB per effettuare una verifica riguardante i progetti
     * associati ad un projectManager. In particolare viene restituito: "true" se il PM
     * ha progetti in corso associati (almeno uno), "false" in caso contrario.
     *
     * @param idPM int l'id del project manager su cui effettuare la verifica
     * @return String codice che rappresenta il risultato della verifica
     * @author Davide Vitiello
     */
    @Override
    public String verifyProjectsStatus(int idPM) {
        LOGGER.log(Level.INFO, LAYERLBL + " Verify project manager's projects' status");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql = "SELECT status FROM project WHERE project_manager = " + idPM;
            ResultSet rs = utilDB.query(stmt, sql);	//esecuzione del comando SQL

            while (rs.next()) {
                if (rs.getString(1).equals("in corso")) {
                    return "true";
                }
            }
            
            return "false";
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

    }

    /**
     * Il metodo deleteTimesheet(int idUser) sfrutta i metodi forniti dalla classe UtilDB
     * per eliminare il timesheet di un utente specifico nel Database.
     * @param idUser int
     * @return String esito della cancellazione
     * @author Lorenzo Svezia
     */
    @Override
    public String deleteTimesheet(int idUser) {
        LOGGER.log(Level.INFO, LAYERLBL + "delete user's timesheet");
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //SQL delete
            String sql = "DELETE FROM timesheetCell WHERE user=" + idUser;
            //esecuzione del comando SQL
            utilDB.manipulate(stmt, sql);
        } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
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
    
}
