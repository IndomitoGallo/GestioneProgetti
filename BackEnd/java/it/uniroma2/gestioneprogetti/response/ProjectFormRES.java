/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.gestioneprogetti.response;

import java.util.List;

/**
 *
 * @author F.Camerlengo
 */
public class ProjectFormRES extends AbstractRES {
    
    private ProjectRES project;
    private List<UserRES> employees;
    private List<UserRES> pms;
    private int[] empolyeesAssociation;

    public ProjectRES getProject() {
        return project;
    }

    public void setProject(ProjectRES project) {
        this.project = project;
    }

    public List<UserRES> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserRES> employees) {
        this.employees = employees;
    }

    public List<UserRES> getPms() {
        return pms;
    }

    public void setPms(List<UserRES> pms) {
        this.pms = pms;
    }

    public int[] getEmpolyeesAssociation() {
        return empolyeesAssociation;
    }

    public void setEmpolyeesAssociation(int[] empolyeesAssociation) {
        this.empolyeesAssociation = empolyeesAssociation;
    }
    
    
}
