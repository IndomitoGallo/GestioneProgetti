package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.User;
import java.util.List;

public interface IUserDAO {  
    
    List<User> displayUsers();
    String insertUser(User user);
    String updateUser(User user);
    String deleteUser(User user);
    String retrieveUser(User user);
    String verifyCreationData(String user, String mail);
    String verifyLoginData(String user, String pwd, int profile);
    String verifyUpdateData(int idUser, String user, String mail);
    String deactivateUser(int idUser);
    String insertProfilesAssociation(int idUser, int[] profiles);
    int[] retrieveProfilesAssociation(int idUser);
    String updateProfilesAssociation(int idUser, int[] profiles);
    String verifyProfiles(int idUser);
    String verifyProjectsStatus(int idPM);
    String deleteTimesheet(int idUser);
    
}
