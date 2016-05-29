package it.uniroma2.gestioneprogettiandroid.dao;

import java.io.IOException;
import java.util.List;

import it.uniroma2.gestioneprogettiandroid.domain.Project;
import it.uniroma2.gestioneprogettiandroid.domain.ProjectDetails;
import it.uniroma2.gestioneprogettiandroid.exception.InvalidTokenException;
import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;

/**
 * Created by gaudo on 21/05/16.
 */
public interface IProjectDAO {

    List<Project> getPMProjects(String sessionId) throws IOException, ServiceUnavailableException, InvalidTokenException;
    ProjectDetails getPMProjectById(int id, String sessionId) throws IOException, ServiceUnavailableException, InvalidTokenException;

}
