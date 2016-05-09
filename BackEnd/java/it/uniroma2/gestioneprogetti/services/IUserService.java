package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.UserProfilesRQS;
import it.uniroma2.gestioneprogetti.request.UserRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindUserRES;
import it.uniroma2.gestioneprogetti.response.UserProfilesRES;

public interface IUserService {
    
    public EmptyRES insertUser(UserProfilesRQS request);
    public EmptyRES updateUser(UserProfilesRQS request);
    public UserProfilesRES retrieveUser(UserRQS request);
    public FindUserRES displayUsers(EmptyRQS request);
    public EmptyRES verifyLoginData(UserRQS request);
    
}
