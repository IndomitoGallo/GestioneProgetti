package it.uniroma2.gestioneprogetti.controller;

import java.util.HashMap;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Calendar;
        
/**
 * Classe che gestisce le sessioni degli utenti connessi all'applicazione
 * tramite due mappe:
 * la prima con valori alfanumerici identificativi della sessione stessa insieme a data
 * e ora del login;
 * la seconda con lo stesso valore alfanumerico, ma con l'id dell'utente loggato. 
 * Per evitare che lo stesso utente, loggandosi più volte, lasci per un tempo indefinito
 * questi id nella mappa, periodicamente avviene un controllo sul tempo di permanenza degli
 * id. Se supera le 2 ore, la sessione viene brutalmente chiusa.
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0
 */
public class SessionController {
    
    private static HashMap<String,Calendar> sessionTime = new HashMap<>();
    private static HashMap<String,Integer> sessionUser = new HashMap<>();
    
    /**
     * Il metodo add(int userId) crea un id di sessione rappresentato da una stringa alfanumerica che
     * viene inserita come chiave delle mappe, insieme alla data e all'ora correnti e all'id dell'utente.
     * L'id di sessione generato viene poi restituito al Controller che ha invocato tale metodo.
     * Nota: prima di creare la nuova sessione viene effettuato il controllo sulla durata della
     * permanenza delle altre sessioni, chiamando il metodo expire.
     * @param userId int id dell'utente
     * @return String id della sessione 
     * @author Luca Talocci, Lorenzo Bernabei
     */
    public static String add(int userId) {
        expire();
        String sessionId = RandomStringUtils.randomAlphanumeric(20);
        sessionTime.put(sessionId, Calendar.getInstance());
        sessionUser.put(sessionId, userId);
        return sessionId;
        
    }
    
    /**
     * Il metodo remove(String sessionId) riceve in ingresso l'id di sessione dell'utente
     * che vuole fare logout e cancella dalle mappe l'id di sessione in modo da chiuderla
     * definitivamente.
     * @param sessionId String id di sessione dell'utente che vuole fare logout
     * @author Luca Talocci, Lorenzo Bernabei
     */
    public static void remove(String sessionId) {        
        sessionTime.remove(sessionId);  
        sessionUser.remove(sessionId);
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
        return sessionTime.containsKey(sessionId) && sessionUser.containsKey(sessionId);       
    }
    
    /**
     * Il metodo expire() itera sulle coppie della prima mappa e, se trova una sessione aperta da più di
     * due ore, la chiude eliminando le coppie da entrambe le mappe.
     */
    private static void expire() {
        for (HashMap.Entry<String,Calendar> pair : sessionTime.entrySet()) {
            long timeNow = Calendar.getInstance().getTimeInMillis();
            long timeSession = pair.getValue().getTimeInMillis();
            long soglia = 7200000;
            if((timeNow - timeSession) > soglia) {
                sessionTime.remove(pair.getKey());
                sessionUser.remove(pair.getKey());
            }
        }
    }
    
    /**
     * Il metodo getUserId(String sessionId) interroga la mappa sessionUser per
     * ottenere l'id dell'utente associato ad un dato id di sessione.
     * @param sessionId
     * @return 
     */
    public static int getUserId(String sessionId) {
        return sessionUser.get(sessionId);
    }
    
}
