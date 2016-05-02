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

@Repository("userDAO")
public class UserDAO implements IUserDAO{
 
    private final static Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private final static String LAYERLBL = "****DAO LAYER**** ";
    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "FAIL";
    
    //Qui di seguito viene iniettata l'iniezione della dipendenza di UtilDB
    @Autowired
    private UtilDB utilDB;
    
    /**
     * Il metodo displayUsers() sfrutta i metodi forniti dalla classe UtilDB
     * per estrapolare la lista degli utenti dal Database.
     * Notare che per "utenti" si intendono i profili diversi da "Controller" e "Amministratore".
     * @return List<User>
     * @author Luca Talocci
     */
    @Override
    public List<User> displayUsers() {
        LOGGER.log(Level.INFO, LAYERLBL + "displayUsers");	
        Connection conn=null;
        Statement stmt=null;
        List<User> usersList = null;
        try{
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            String sql = "SELECT * FROM user";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utilDB.query(stmt, sql);
            //setto i campi dell'oggetto del dominio con i dati letti dal database
            usersList = utilDB.resultSetToUserArray(rs);
       } catch(SQLException e){
            System.err.println("Database Error!");
            e.printStackTrace();
            return usersList;
       } finally{
            try{
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return usersList;
            }
        }
        return usersList;
    }

    /**
     * Il metodo insertUser(User u) sfrutta i metodi forniti dalla classe UtilDB
     * per inserire i dati di un utente nel Database.
     * @param user User
     * @return String esito dell'inserimento
     * @author Lorenzo Svezia
     */
    @Override
    public String insertUser(User user) {
        LOGGER.log(Level.INFO, LAYERLBL + "insert user {0}", user.getName());

        Connection conn = null;	
        Statement stmt = null;	
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //inserimento SQL
            String sql = "INSERT INTO user VALUES(NULL, '"  + user.getUsername() + "', '" +
                                                              user.getName() + "', '" + 
                                                              user.getSurname() + "', '" +
                                                              user.getPassword() + "', '" + 
                                                              user.getEmail() + "', '" +
                                                              user.getSkill() + "')";
            utilDB.manipulate(stmt, sql);	//esecuzione del comando SQL
            sql = "SELECT id FROM user WHERE username='" + user.getUsername() + "'";
            ResultSet rs = utilDB.query(stmt, sql);
            rs.next();
            user.setId(rs.getInt(1)); //set del campo id dell'utente
        } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
        } finally {
            try {
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return "fail";
            } 
        }		
	return "success";
    }

    /**
     * Il metodo updateUser(User user) sfrutta i metodi forniti dalla classe UtilDB
     * per modificare i dati di un utente nel Database.
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
            //SQL insert
            String sql = "UPDATE user SET name='" + user.getName() + "', " + 
                                         "surname='" + user.getSurname() + "', " +
                                         "username='" + user.getUsername() + "', " +
                                         "email='" + user.getEmail() + "', " +
                                         "password='" + user.getPassword() + "', " + 
                                         "skill='" + user.getSkill() + ", " +
                                         "isDeactivated=" + user.getIsDeactivated() + ", " +
                                         "seniority=" + user.getSeniority() + " " + 
                                         "WHERE id=" + user.getId();
            utilDB.manipulate(stmt, sql); //esecuzione del comando SQL
        } catch (SQLException e) {  //il metodo intercetta un'eccezione proveniente dal DB         
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
     * Il metodo deleteUser(User u) sfrutta i metodi forniti dalla classe UtilDB
     * per eliminare i dati di un utente specifico nel Database.
     * @param user User
     * @return String esito della cancellazione
     * @author Lorenzo Svezia
     */
    public String deleteUser(User user) {	    
        LOGGER.log(Level.INFO, LAYERLBL + "delete user {0}", user.getName());

        Connection conn = null;	
        Statement stmt = null;
        try {
            conn = utilDB.createConnection();	//connessione al DB
            stmt = conn.createStatement();	//creazione dello Statement
            //SQL delete
            String sql = "DELETE FROM user WHERE id=" + user.getId();
            //esecuzione del comando SQL
            if(utilDB.manipulate(stmt, sql) == 0){ 
                //se la query non ha interessato alcun record del DB, viene restituita una stringa di errore
                return "fail";
        }
        } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
        } finally {
            try{
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return "fail";
            }
        }		
        return "success";    
    }
    
    /**
     *  Effettua l'operazione di retrieve, ovvero il recupero dei dati dell'utente
     * passato come argomento settando tutti i parametri di esso.
     * Restituisce SUCCESS se il recupero e il settaggio dei dati e' andato a buon fine, FAIL altrimenti.
     * @param u User
     * @return String esito del recupero dei dati
     * @autor Francesco Gaudenzi
     */ 
    @Override
    public String retrieveUser(User u){
        LOGGER.log(Level.INFO, LAYERLBL + "retrieve User");	
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
     * Il metodo verifyLoginData(String user, String pwd, int profile) verifica che i dati di Login inseriti 
     * dall'utente corrispondano ai dati immessi in fase di registrazione dall'utente che 
     * deve effettuare l'accesso. 
     * Il valore di ritorno è "true" se i dati di login sono corretti, "false" altrimenti.
     * In caso di eccezioni viene restituito fail.
     * @param user String lo username dell'utente
     * @param pwd String la password dell'utente
     * @param profile int il profilo con cui l'utente vuole accedere
     * @return String esito della verifica
     * @author Luca Talocci
     */
    @Override
    public String verifyLoginData(String user, String pwd, int profile) {
        LOGGER.log(Level.INFO, LAYERLBL + "Verify user login data");  
        Connection conn = null; 
        Statement stmt = null;
        try {
            conn = utilDB.createConnection(); //connessione al DB
            stmt = conn.createStatement();  //creazione dello Statement
            //SQL select
            String sql1 = "SELECT id FROM user WHERE username='" + user + "' AND " +
                                                    "password='" + pwd + "'";
            //memorizzazione del risultato delle query in un ResultSet
            ResultSet rs1 = utilDB.query(stmt, sql1);
            if(!rs1.next())
                return "data_fail"; //username o password passati in ingresso al metodo inesistenti
            //Verifico che l'utente sia associato al profilo richiesto
            String sql2 = "SELECT * FROM profileUser WHERE user=" + rs1.getInt(1) + " AND profile=" + profile;
            ResultSet rs2 = utilDB.query(stmt, sql2);
            if(!rs2.next())
                return "unauthorized_profile"; //il profilo richiesto non è associato all'utente
        } catch (SQLException e) {      //catch di un'eccezione proveniente dal DB         
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
        //username e password passati in ingresso al metodo sono presenti nel database 
        //e l'utente è associato al profilo richiesto
        return "true"; 
    }
    
    /**
     * Il metodo verifyCreationData(String user, String mail) verifica che i dati di registrazione inseriti 
     * dall'utente, in particolare username e email, non siano già presenti nel database.
     * Il valore di ritorno è "true" se i dati di registrazione sono corretti, "false" altrimenti.
     * In caso di eccezioni viene restituito fail.
     * @param user String lo username dell'utente
     * @param mail String l'e-mail dell'utente
     * @return String esito della verifica
     * @author Lorenzo Bernabei
     */
    public String verifyCreationData(String user, String mail) {
        LOGGER.log(Level.INFO, LAYERLBL + "Verify project creation data");	
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
            if(rs1.next())
                return "false";	//username passato in ingresso al metodo già presente nel database
            ResultSet rs2 = utilDB.query(stmt, sql2);
            if(rs2.next())
                return "false";	//mail passata in ingresso al metodo già presente nel database
            return "true"; //username e mail passati in ingresso al metodo non sono presenti nel database
        } catch (SQLException e) {      //catch di un'eccezione proveniente dal DB	    	 
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
     * Il metodo verifyUpdateData(int idUser, String user, String mail) verifica che i dati di aggiornamento del
     * profilo inseriti dall'utente, in particolare username e email, non siano già presenti nel database.
     * Il valore di ritorno è true se i dati inseriti sono corretti, false altrimenti.
     * In caso di errori, viene sollevato un RuntimeException.
     * @param idUser int l'id dell'utente, per poter effettuare la verifica su tutti gli altri utenti
     * @param user String lo username dell'utente
     * @param mail String l'e-mail dell'utente
     * @return boolean esito della verifica
     * @author Francesco Gaudenzi
     */
    @Override
    public boolean verifyUpdateData(int idUser, String user, String mail) { 
        Connection conn = null; 
        Statement stmt = null;
        try {
            conn= utilDB.createConnection();   //connection to DB
            stmt=conn.createStatement();    //creazione dello Statement
            //SQL select
            String sql1 = "SELECT * FROM user WHERE username='" + user + "' AND id<>" + idUser;
            String sql2 = "SELECT * FROM user WHERE email='" + mail + "' AND id<>" + idUser;
            //memorizzazione del risultato delle query in un ResultSet
            ResultSet rs1 = utilDB.query(stmt, sql1);
            if(rs1.next())
                return false; //username passato in ingresso al metodo già presente nel database
            ResultSet rs2 = utilDB.query(stmt, sql2);
            if(rs2.next())
                return false; //mail passata in ingresso al metodo già presente nel database
            return true; //username e mail passati in ingresso al metodo non sono presenti nel database
        } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB 
            System.err.println("Database Error!");
            throw new RuntimeException(e);
        } finally {
            try{
                if(stmt!=null)
                    utilDB.closeStatement(stmt);
                if(conn!=null)
                    utilDB.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Effettua la cancellazione logica dell'utente che ha associato l'id passato in ingresso, ovvero l'utente
     * non viene cancellato, ma viene settato nel database un flag che lo disattiva.
     * Restituisce SUCCESS se la cancellazione è andata a buon fine, FAIL altrimenti.
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
            String update = "UPDATE user set isDeactiveted=true where id=" + idUser;
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
    
}
