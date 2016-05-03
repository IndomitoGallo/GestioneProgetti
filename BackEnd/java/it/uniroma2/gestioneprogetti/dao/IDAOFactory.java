package it.uniroma2.gestioneprogetti.dao;

public interface IDAOFactory {    
    public IUserDAO getUserDao();   
    public IProjectDAO getProjectDao(); 
    public ITimesheetDAO getTimesheetDao(); 
}
