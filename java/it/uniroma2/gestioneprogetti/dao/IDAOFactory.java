package it.uniroma2.gestioneprogetti.dao;

public interface IDAOFactory {    
    public IProjectDAO getUserDao();   
    public IProjectDAO getProjectDao(); 
    public ITimesheetDAO getTimesheetDao(); 
}
