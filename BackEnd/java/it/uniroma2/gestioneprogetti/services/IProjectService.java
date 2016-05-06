package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindProjectsRES;


public interface IProjectService {
    
    EmptyRES insertProject(ProjectRQS request);
    EmptyRES updateProject(ProjectRQS request);
    FindProjectsRES findAllProjects(EmptyRQS request);
    
}
