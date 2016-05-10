package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindProjectsRES;
import it.uniroma2.gestioneprogetti.response.ProjectRES;

public interface IProjectService {

    EmptyRES insertProject(ProjectRQS request);
    EmptyRES updateProject(ProjectRQS request);
    EmptyRES deleteProject(ProjectRQS requeste);
    ProjectRES retrieveProject(ProjectRQS request);
    FindProjectsRES displayProjects(EmptyRQS request);

}
