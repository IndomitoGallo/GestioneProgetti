package it.uniroma2.gestioneprogetti.response;
/**
 * La classe UserProfilesRES modella un oggetto response che incapsula al suo interno 
 * le proprietà UserRES "utente" e int[] "profili" in modo tale
 * da trasportare i dati dallo strato Application allo strato Services.
 * @author Gruppo Talocci
 */
public class UserProfilesRES extends AbstractRES {

    private UserRES user;
    private int[] profiles;

    public UserProfilesRES() {
    
    }
    
    public UserProfilesRES(UserRES user, int[] profiles) {
        this.user = user;
        this.profiles = profiles;
    }

    public UserRES getUser() {
        return user;
    }
 
    public void setUser(UserRES user) {
        this.user = user;
    }

    public int[] getProfiles() {
        return profiles;
    }

    public void setProfiles(int[] profiles) {
        this.profiles = profiles;
    }    
    
}
