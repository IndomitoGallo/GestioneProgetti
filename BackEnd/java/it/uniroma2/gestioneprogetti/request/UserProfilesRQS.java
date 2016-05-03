package it.uniroma2.gestioneprogetti.request;


public class UserProfilesRQS extends AbstractRQS {

    private UserRQS user;
    private int[] profiles;

    public UserProfilesRQS() {
    
    }
    
    public UserProfilesRQS(UserRQS user, int[] profiles) {
        this.user = user;
        this.profiles = profiles;
    }

    public UserRQS getUser() {
        return user;
    }
 
    public void setUser(UserRQS user) {
        this.user = user;
    }

    public int[] getProfiles() {
        return profiles;
    }

    public void setProfiles(int[] profiles) {
        this.profiles = profiles;
    }    
    
}
