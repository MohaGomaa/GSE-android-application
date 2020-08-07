package com.example.mohamedahmedgomaa.servieclyapp.model;

public class Vaccination  {
    private String Child_Name;
    private String Vaccination_Type;
    private String Heathcare ;
    private String Scedual;
    private String code;
    private String vaccinated;
    private int id;
    public Vaccination() {
    }

    public Vaccination(int id ,String child_Name, String vaccination_Type, String heathcare, String scedual, String code,String vaccinated) {
        this.Child_Name = child_Name;
        this.Vaccination_Type = vaccination_Type;
        this.Heathcare = heathcare;
        this.Scedual = scedual;
        this.code = code;
        this.vaccinated=vaccinated;
        this.id=id;
    }

    public String getChild_Name() {
        return Child_Name;
    }

    public void setChild_Name(String child_Name) {
        Child_Name = child_Name;
    }

    public String getVaccination_Type() {
        return Vaccination_Type;
    }

    public void setVaccination_Type(String vaccination_Type) {
        Vaccination_Type = vaccination_Type;
    }

    public String getHeathcare() {
        return Heathcare;
    }

    public void setHeathcare(String heathcare) {
        Heathcare = heathcare;
    }

    public String getScedual() {
        return Scedual;
    }

    public void setScedual(String scedual) {
        Scedual = scedual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String date) {
        this.code = date;
    }

    public String getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(String vaccinated) {
        this.vaccinated = vaccinated;
    }
}
