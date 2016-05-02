package it.uniroma2.gestioneprogetti.util;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * IUtilDB è un'interfaccia che dichiara dei metodi standard che facilitano la
 * gestione delle operazioni sul database.
 * @author Luca Talocci, Lorenzo Bernabei, Luca Camerlengo, Davide Vitiello
 * @version 4.0 10/02/2016
 */
public interface IUtilDB {
    
    public Connection createConnection();
    public Statement createStatement(Connection conn);
    public ResultSet query(Statement stmt, String qry);
    public int manipulate(Statement stmt, String qry);
    public void closeStatement(Statement stmt);
    public void closeConnection(Connection conn);
    public void printResultSet(ResultSet rs);
    public ArrayList<String> resultSetToArrayString(ResultSet rs);
    public void printTables(Connection conn);
    public void printTableColumnsNames(Connection conn, String tableName);
    public List<Project> resultSetToProjectArray(ResultSet rs);
    public List<User> resultSetToUserArray(ResultSet rs);
    
}
