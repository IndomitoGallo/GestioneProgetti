package it.uniroma2.gestioneprogetti.services;


public interface IServiceFactory {
    
    IUserService getUserService();
    IProjectService getProjectService();
    
}
