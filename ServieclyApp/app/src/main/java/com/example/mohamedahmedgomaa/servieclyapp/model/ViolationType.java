package com.example.mohamedahmedgomaa.servieclyapp.model;

public class ViolationType {
    private String ViolationName;
    private int Id;
    private String ViolationPrice ;

    public ViolationType() {
    }

    public ViolationType(String violationName, int id, String violationPrice) {
        ViolationName = violationName;
        Id = id;
        ViolationPrice = violationPrice;
    }

    public String getViolationName() {
        return ViolationName;
    }

    public void setViolationName(String violationName) {
        ViolationName = violationName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getViolationPrice() {
        return ViolationPrice;
    }

    public void setViolationPrice(String violationPrice) {
        ViolationPrice = violationPrice;
    }
}
