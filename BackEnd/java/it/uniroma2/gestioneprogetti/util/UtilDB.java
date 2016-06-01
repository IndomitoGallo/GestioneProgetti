package it.uniroma2.gestioneprogetti.util;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * La classe UtilDB e' un'utility di accesso al database implementata come 
 * una factory con il pattern singleton. Nell'architettura dell'applicazione 
 * si pone nello strato “Domain” tra il DAO e lo strato di “Persistence”.
 *
 * @author Luca Talocci, Lorenzo Bernabei, Luca Camerlengo, Davide Vitiello
 * @version 4.0 10/02/2016
 */
@Component("utilDB")
@Scope("singleton")
public class UtilDB {

    @Autowired
    DataSource dataSource;
    
    /**
     * Il metodo crea una connessione ad un DB, richiamando semplicemente 
     * il metodo getConnection dell’attributo dataSource.
     *
     * @return conn Connection to DB
     * @throws SQLException
     * @see com.mysql.jdbc.Driver
     * @see Config
     */
    public Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Il metodo permette di creare uno Statement associato alla connessione passata in ingresso. 
     * Lo Statement e' un oggetto necessario per poter eseguire query mysql.
     *
     * @param conn Connection to DB
     * @return Statement associato alla connessione in ingresso
     * @throws SQLException
     * @see java.sql.Statement
     */
    public Statement createStatement(Connection conn) throws SQLException {
        return conn.createStatement();
    }

    /**
     * Il metodo ha il compito di eseguire una query sul DB, 
     * il cui risultato e' un oggetto di tipo ResultSet 
     * che contiene i record restituiti dalla query.
     *
     * @param stmt Statement associato ad una connessione al database
     * @param qry String query in linguaggio MySQL
     * @return ResultSet oggetto che contiene l'insieme dei record risultanti
     * dalla query qry
     * @throws SQLException
     * @see java.sql.Statement
     */
    public ResultSet query(Statement stmt, String qry) throws SQLException {
        return stmt.executeQuery(qry);
    }

    /**
     * Il metodo gestisce le operazioni di Insert, Update e Delete sul DB.
     *
     * @param stmt Statement associato ad una connessione al database
     * @param qry String query in linguaggio MySQL
     * @return int numero di righe(tuple/records) coinvolte nella query
     * @throws SQLException
     * @see java.sql.Statement#executeUpdate(java.lang.String)
     */
    public int manipulate(Statement stmt, String qry) throws SQLException {
        return stmt.executeUpdate(qry);
    }

    /**
     * Il metodo permette di chiudere lo Statement passato in ingresso.
     *
     * @param stmt Statement che si vuole chiudere
     * @throws SQLException
     * @see java.sql.Statement#close()
     */
    public void closeStatement(Statement stmt) throws SQLException {
        stmt.close();
    }

    /**
     * Il metodo ha il compito di chiudere la connessione al DB. 
     * Chiudere la connessione può essere necessario poiché un DBMS può 
     * gestire soltanto un pool predefinito di connessioni in parallelo.
     *
     * @param conn Connection to DB
     * @throws SQLException
     * @see java.sql.Connection#close()
     */
    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    /**
     * Il metodo stampa a schermo il resultSet in ingresso in modo formattato,
     * separando i campi di ogni record con il carattere "|".
     *
     * @param rs ResultSet prodotto da una query
     * @throws SQLException
     * @see java.sql.ResultSet
     */
    public void printResultSet(ResultSet rs) throws SQLException {
        //memorizzo nell'oggetto resM i MetaData (ovvero le meta-informazioni) sul ResultSet
        ResultSetMetaData resM = rs.getMetaData();
        //in questo esempio prendiamo dalle meta-informazioni il numero di colonne del ResultSet
        int colonne = resM.getColumnCount();
        for (int i = 0; i < colonne; i++) {
            System.out.print(resM.getColumnName(i + 1) + " | ");
        }
        System.out.println();
        while (rs.next()) {
            for (int i = 0; i < colonne; i++) {
                System.out.print(rs.getString(i + 1) + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Il metodo prende in ingresso un oggetto di tipo ResultSet e restituisce
     * un ArrayList di oggetti di tipo String, dove ogni Stringa e' un record
     * del ResultSet L'esistenza del metodo e' finalizzata a rendere la gestione
     * dei risultati delle query sul DB piu' semplici da parte dell'applicazione
     * Il metodo ottiene in primo luogo un oggetto di tipo ResultSetMetaData a
     * partire dal ResultSet rs in ingresso Esso permette di conoscere il numero
     * di colonne del ResultSet rs, in modo da separare, per ogni stringa
     * nell'ArrayList di ritorno, i campi del record (tramite '*').
     *
     * @param rs ResultSet prodotto da una query
     * @return ArrayList<String> collezione di oggetti di tipo String
     * @throws SQLException
     * @see java.sql.ResultSet#getMetaData()
     * @see java.sql.ResultSetMetaData
     */
    public ArrayList<String> resultSetToArrayString(ResultSet rs) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        String str = "";
        ResultSetMetaData resM = rs.getMetaData();
        int colonne = resM.getColumnCount();
        for (int i = 0; i < colonne; i++) {
            str = str + resM.getColumnName(i + 1) + "*";
        }
        result.add(str);
        while (rs.next()) {
            str = "";
            for (int i = 0; i < colonne; i++) {
                str = str + rs.getString(i + 1) + "*";
            }
            result.add(str);
        }
        return result;
    }

    /**
     * Il metodo stampa a schermo la lista di tutte le tabelle del DataBase, una
     * per riga.
     *
     * @param conn Connection to DB
     * @see java.sql.ResultSet#getString(int)
     * @throws SQLException
     */
    public void printTables(Connection conn) throws SQLException {
        //memorizziamo nell'oggetto dbmb le meta-informazioni sul database
        DatabaseMetaData dbmd = conn.getMetaData();

        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = null;
        String[] types = null;

        ResultSet rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types);

        System.out.println("Tables:");
        while (rs.next()) {
            /* ad ogni iterazione tableName conterra' la colonna
                     * di indice 3 di result, ovvero, quella contenente
                     * il nome della tabella del db
             */
            String tableName = rs.getString(3);
            System.out.println(tableName);
        }
    }

    /**
     * Il metodo che stampa a schermo i nomi di tutte le colonne presenti in una
     * tabella.
     *
     * @param conn Connection to DB
     * @param tableName String nome esatto della tabella
     * @throws SQLException
     * @see java.sql.DatabaseMetaData#getColumns(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    public void printTableColumnsNames(Connection conn, String tableName) throws SQLException {
        //memorizziamo nell'oggetto dbmb le meta-informazioni sul database
        DatabaseMetaData dbmd = conn.getMetaData();

        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = tableName;
        String columnNamePattern = null;

        ResultSet rs = dbmd.getColumns(catalog, schemaPattern,
                tableNamePattern, columnNamePattern);
        System.out.println("Columns of " + tableName + ":");
        while (rs.next()) {
            String columnName = rs.getString(4); // il nome della colonna
            System.out.println(columnName);
        }
    }
    
    /**
     * Il metodo converte un ResultSet contenente record della tabella Project
     * in una lista di progetti.
     * @param rs
     * @return List<Project>
     * @throws SQLException
     */
    public List<Project> resultSetToProjectArray(ResultSet rs) throws SQLException {
        List<Project> projects = new ArrayList<Project>();
        while (rs.next())
            projects.add(
                    new Project(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                rs.getDouble(5), rs.getDouble(6), rs.getInt(7)));
        return projects;
    }
    /**
     * Il metodo converte un ResultSet contenente record della tabella User
     * in una lista di utenti.
     * @param rs
     * @return List<User>
     * @throws SQLException
     */
    public List<User> resultSetToUserArray(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<User>();
        while (rs.next())
            users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), 
                               rs.getString(4), rs.getString(5), rs.getString(6), 
                               rs.getString(7), rs.getBoolean(8)));
        return users;
    }
    

}
