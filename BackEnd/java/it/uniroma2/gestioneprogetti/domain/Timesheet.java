package it.uniroma2.gestioneprogetti.domain;

import java.util.Date;

/**
 * La classe Timesheet modella la rispettiva entita' del Database e rappresenta
 * quindi una delle classi del dominio dell'applicazione.
 * @author Lorenzo Svezia
 */
public class Timesheet {
    
    private int id;
    private int absence;
    private int project;
    private int user;
    private Date date;
    private String content; 

    public Timesheet() {
    }
    
    public Timesheet(int id, int absence, int project, int user, Date date, String content) {
        
        this.id = id;
        this.absence = absence;
        this.project = project;
        this.user = user;
        this.date = date;
        this.content = content;
        
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getUser() {
        return user;
   }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    
}

    