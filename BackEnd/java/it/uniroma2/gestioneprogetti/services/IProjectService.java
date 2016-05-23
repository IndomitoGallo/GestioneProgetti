package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectEmployeesRQS;
import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindProjectsRES;
import it.uniroma2.gestioneprogetti.response.PMsEmployeesRES;
import it.uniroma2.gestioneprogetti.response.ProjectEmployeesRES;
import it.uniroma2.gestioneprogetti.response.ProjectFormRES;

public interface IProjectService {

    EmptyRES insertProject(ProjectEmployeesRQS request);
    EmptyRES updateProject(ProjectEmployeesRQS request);
    ProjectEmployeesRES displayProject(ProjectRQS request);
    PMsEmployeesRES displayPMsEmployees(EmptyRQS request);
    FindProjectsRES displayProjects(EmptyRQS request);
    ProjectFormRES displayProjectForm(ProjectRQS request);
    FindProjectsRES displayPMProjects(ProjectRQS request);

}
