package it.uniroma2.gestioneprogettiandroid.domain;

public class Dipendente {
    private String name;
    private int hours;

    public Dipendente(String name, int hours)
    {
        this.name = name;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

}
