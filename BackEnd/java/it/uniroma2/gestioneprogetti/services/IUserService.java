package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.UserProfilesRQS;
import it.uniroma2.gestioneprogetti.request.UserRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindUsersRES;
import it.uniroma2.gestioneprogetti.response.UserProfilesRES;
import it.uniroma2.gestioneprogetti.response.UserRES;

public interface IUserService {
    
    public EmptyRES insertUser(UserProfilesRQS request);
    public EmptyRES updateUser(UserProfilesRQS request);
    public EmptyRES deleteUser(UserRQS request);
    public UserProfilesRES displayUser(UserRQS request);
    public FindUsersRES displayUsers(EmptyRQS request);
    public UserRES verifyLoginData(UserRQS request);
    
}
