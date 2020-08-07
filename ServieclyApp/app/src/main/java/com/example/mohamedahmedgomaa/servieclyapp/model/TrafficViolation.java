package com.example.mohamedahmedgomaa.servieclyapp.model;

public class TrafficViolation {
    private String Type_Violation;
    private String Car_code;
    private String price ;
    private String date;
    private String Is_Paid;
    private String Citizen_Name;
    private String national_id;

    public TrafficViolation() {
    }

    public TrafficViolation( String Type_Violation, String Car_code, String price, String date, String Is_Paid,String Citizen_Name,String national_id) {
        this.Type_Violation = Type_Violation;
        this.Car_code = Car_code;
        this.price = price;
        this.date = date;
        this.Is_Paid = Is_Paid;
        this.Citizen_Name = Citizen_Name;
        this.national_id = national_id;


    }

    public String getType_Violation() {
        return Type_Violation;
    }

    public void setType_Violation(String type_Violation) {
        Type_Violation = type_Violation;
    }

    public String getCar_code() {
        return Car_code;
    }

    public void setCar_code(String car_code) {
        Car_code = car_code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIs_Paid() {
        return Is_Paid;
    }

    public void setIs_Paid(String is_Paid) {
        Is_Paid = is_Paid;
    }

    public String getCitizen_Name() {
        return Citizen_Name;
    }

    public void setCitizen_Name(String citizen_Name) {
        Citizen_Name = citizen_Name;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }
}
