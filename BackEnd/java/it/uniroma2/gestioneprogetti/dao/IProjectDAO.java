package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.domain.User;
import java.util.List;

public interface IProjectDAO {  
    List<Project> displayProjects();
    String insertProject(Project project);
    String updateProject(Project project);
    String deleteProject(Project project);
    String retrieveProject(Project project);
    List<Project> displayPMProjects(int idPM);
    List<List<User>> displayPMsEmployees();
    String updateEmployeesAssociation(int idProject, int[] association);
    String insertEmployeesAssociation(int idProject, int[] employees);
    int[] retrieveEmployeesAssociation(int idProject);
    List<List<Integer>> retrieveEmployeesAndHours(int idProject);
    String retrievePMName(int idPM);
}
