package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.User;
import java.util.List;

public interface IUserDAO {  
    public List<User> displayUsers();
    public String insertUser(User user);
    public String updateUser(User user);
    public String deleteUser(User user);
    public String retrieveUser(User user);
    public String verifyCreationData(String user, String mail);
    public String verifyLoginData(String user, String pwd, int profile);
    public boolean verifyUpdateData(int idUser, String user, String mail);
    public String deactivateUser(int idUser);
}
