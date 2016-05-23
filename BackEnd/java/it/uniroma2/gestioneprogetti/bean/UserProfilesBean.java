package it.uniroma2.gestioneprogetti.bean;

import it.uniroma2.gestioneprogetti.domain.User;
/**
 * La classe UserProfilesBean è utilizzata per incapsulare le richieste e le 
 * response HTTP che sono associate al Front-End.
 * Essa contiene le proprietà User "utente", int[] "profili" e String "id sessione".
 * @author Gruppo Talocci
 */
public class UserProfilesBean {
    private User user;
    private int[] profiles;
    private String sessionId;
    
    public UserProfilesBean() {    
    }
    
    public UserProfilesBean(User user, int[] profiles) {
        this.user = user;
        this.profiles = profiles;
    }

    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }

    public int[] getProfiles() {
        return profiles;
    }

    public void setProfiles(int[] profiles) {
        this.profiles = profiles;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
