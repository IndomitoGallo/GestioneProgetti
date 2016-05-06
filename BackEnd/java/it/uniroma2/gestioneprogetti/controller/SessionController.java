package it.uniroma2.gestioneprogetti.controller;

import java.util.HashMap;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Classe che gestisce le sessioni degli utenti connessi all'applicazione
 * tramite un dizionario implementato con una struttura dati HashMap<key, value>,
 * dove la key è l'id della sessione e value rappresenta il profilo dell'utente connesso. 
 * @author Luca Talocci
 * @version 1.0
 */
public class SessionController {
    
    public static HashMap<String, String> sessions;
    
    /**
     * Il metodo add(String profile) riceve in ingresso il profilo dell'utente che vuole loggarsi
     * e crea un id di sessione rappresentato da una stringa alfanumerica che viene inserito 
     * insieme al profilo dell'utente nell'HashMap. L'id di sessione generato viene poi restituito 
     * al Controller che ha invocato tale metodo.
     * @param profile String profilo dell'utente che vuole loggarsi
     * @return String id della sessione 
     * @author Luca Talocci
     */
    public static String add(String profile) {
        
        String sessionId = RandomStringUtils.randomAlphanumeric(20);
        sessions.put(sessionId, profile);
        return sessionId;
        
    }
    
    /**
     * Il metodo remove(String sessionId) riceve in ingresso l'id di sessione dell'utente
     * che vuole fare logout e cancella dall'HashMap l'id di sessione in modo da chiuderla
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
        
        return sessions.containsKey(sessionId);
        
    }
    
}
