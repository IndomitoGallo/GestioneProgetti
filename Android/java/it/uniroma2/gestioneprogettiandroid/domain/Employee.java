package it.uniroma2.gestioneprogettiandroid.domain;

/**
 * Questa classe fa parte del dominio dell'applicazione e rappresenta
 * il dipendente.
 */
public class Employee {
    private final int id;
    private final String username;
    private final String password;
    private final String email;
    private final String name;
    private final String surname;
    private final String skill;
    private final boolean isDeactivated;
    private final int seniority;

    public Employee(int id, String username, String password, String email, String name, String surname, String skill, boolean isDeactivated, int seniority) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.skill = skill;
        this.isDeactivated = isDeactivated;
        this.seniority = seniority;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSkill() {
        return skill;
    }

    public int getSeniority() {
        return seniority;
    }

    public boolean isDeactivated() {
        return isDeactivated;
    }

}
