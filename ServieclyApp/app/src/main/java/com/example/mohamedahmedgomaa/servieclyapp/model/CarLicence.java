package com.example.mohamedahmedgomaa.servieclyapp.model;

public class CarLicence {
    private String CarCode;
    private int Id;

    public CarLicence() {
    }

    public CarLicence(String carCode, int id) {
        CarCode = carCode;
        Id = id;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
