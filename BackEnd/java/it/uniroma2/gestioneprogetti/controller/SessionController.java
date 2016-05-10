package it.uniroma2.gestioneprogetti.controller;

import java.util.ArrayList;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Classe che gestisce le sessioni degli utenti connessi all'applicazione
 * tramite una lista di valori numerici identificativi della sessione stessa. 
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0
 */
public class SessionController {
    
    public static ArrayList<String> sessions;
    
    /**
     * Il metodo add() crea un id di sessione rappresentato da una stringa alfanumerica che
     * viene inserita nella lista. L'id di sessione generato viene poi restituito 
     * al Controller che ha invocato tale metodo.
     * @return String id della sessione 
     * @author Luca Talocci
     */
    public static String add() {
        
        String sessionId = RandomStringUtils.randomAlphanumeric(20);
        sessions.add(sessionId);
        return sessionId;
        
    }
    
    /**
     * Il metodo remove(String sessionId) riceve in ingresso l'id di sessione dell'utente
     * che vuole fare logout e cancella dalla lista l'id di sessione in modo da chiuderla
     * definitivamente.
     * @param sessionId String id di sessione dell'utente che vuole fare logout
     * @author Luca Talocci
     */
    public static void remove(String sessionId) {
        
        sessions.remove(sessionId);
        
    }
    
    /**
     * Il metodo verify(String sessionId) offre ai Controller che invocano tale funzione di
     * verificare se l'id di sessione inviato dal FrontEnd è valido oppure no.
     * Valido significa che è un id registrato e quindi legato ad una sessione attiva.
     * @param sessionId
     * @return boolean esito della verifica
     * @author Luca Talocci
     */
    public static boolean verify(String sessionId) {
        
        return sessions.contains(sessionId);
        
    }
    
}
