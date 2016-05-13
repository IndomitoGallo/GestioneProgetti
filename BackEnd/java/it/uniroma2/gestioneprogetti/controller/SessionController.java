package it.uniroma2.gestioneprogetti.controller;

import java.util.HashMap;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Calendar;
        
/**
 * Classe che gestisce le sessioni degli utenti connessi all'applicazione
 * tramite una mappa di valori numerici identificativi della sessione stessa con la data
 * e l'ora del login.
 * Per evitare che lo stesso utente, loggandosi più volte, lasci per un tempo indefinito
 * questi id nella mappa, periodicamente avviene un controllo sul tempo di permanenza degli
 * id. Se supera le 2 ore, la sessione viene brutalmente chiusa.
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0
 */
public class SessionController {
    
    public static HashMap<String,Calendar> sessions = new HashMap<>();
    
    /**
     * Il metodo add() crea un id di sessione rappresentato da una stringa alfanumerica che
     * viene inserita come chiave della mappa, insieme alla data e all'ora correnti.
     * L'id di sessione generato viene poi restituito al Controller che ha invocato tale metodo.
     * Nota: prima di creare la nuova sessione viene effettuato il controllo sulla durata della
     * permanenza delle altre sessioni, chiamando il metodo expire.
     * @return String id della sessione 
     * @author Luca Talocci, Lorenzo Bernabei
     */
    public static String add() {
        expire();
        String sessionId = RandomStringUtils.randomAlphanumeric(20);
        sessions.put(sessionId, Calendar.getInstance());
        return sessionId;
        
    }
    
    /**
     * Il metodo remove(String sessionId) riceve in ingresso l'id di sessione dell'utente
     * che vuole fare logout e cancella dalla mappa l'id di sessione in modo da chiuderla
     * definitivamente.
     * @param sessionId String id di sessione dell'utente che vuole fare logout
     * @author Luca Talocci, Lorenzo Bernabei
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
     * @author Luca Talocci, Lorenzo Bernabei
     */
    public static boolean verify(String sessionId) {        
        return sessions.containsKey(sessionId);       
    }
    
    /**
     * Il metodo expire() itera sulla mappa e, se trova una sessione aperta da più di
     * due ore, la chiude.
     */
    private static void expire() {
        for (HashMap.Entry<String,Calendar> pair : sessions.entrySet()) {
            long timeNow = Calendar.getInstance().getTimeInMillis();
            long timeSession = pair.getValue().getTimeInMillis();
            long soglia = 7200000;
            if((timeNow - timeSession) > soglia)
                sessions.remove(pair.getKey());
        }
    }
    
}
