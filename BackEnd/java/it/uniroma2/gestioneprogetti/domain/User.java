package it.uniroma2.gestioneprogetti.domain;

/**
 * La classe User modella la rispettiva entita' del Database e rappresenta
 * quindi una delle classi del dominio dell'applicazione.
 * @author Luca Talocci
 * @version 1.0 25/04/2016
 */
public class User {
    
    private int id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String skill;
    private Boolean isDeactivated;
    private int seniority;

    public User() {
    }
    
    public User(
            int id, String username, String password, String email, String name, String surname,
            String skill, Boolean isDeactivated) {
        
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.skill = skill;
        this.isDeactivated = isDeactivated;
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Boolean getIsDeactivated() {
        return isDeactivated;
    }

    public void setIsDeactivated(Boolean isDeactivated) {
        this.isDeactivated = isDeactivated;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }
    
    
    
}
