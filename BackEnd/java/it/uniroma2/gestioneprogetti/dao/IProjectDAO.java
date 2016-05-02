package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.Project;
import java.util.List;

public interface IProjectDAO {  
    public List<Project> displayProjects();
    public String insertProject(Project project);
    public String updateProject(Project project);
    public String deleteProject(Project project);
    public String retrieveProject(Project project);
    public List<Project> displayPMProjects(int idPM);
    
}
