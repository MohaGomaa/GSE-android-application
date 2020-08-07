package com.example.mohamedahmedgomaa.servieclyapp.model;

public class PoliceReport {
    private int report_number ;
    private String classification_name;
    private String report_type;
    private String police_department;
    private String date;
    private String Citizen_Name;
    private String national_id;

    public PoliceReport() {
    }

    public PoliceReport(int report_number, String classification_name, String report_type, String police_department, String date, String citizen_Name, String national_id) {
        this.report_number = report_number;
        this.classification_name = classification_name;
        this.report_type = report_type;
        this.police_department = police_department;
        this.date = date;
        this.Citizen_Name = citizen_Name;
        this.national_id = national_id;
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

    public int getReport_number() {
        return report_number;
    }

    public void setReport_number(int report_number) {
        this.report_number = report_number;
    }

    public String getClassification_name() {
        return classification_name;
    }

    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getPolice_department() {
        return police_department;
    }

    public void setPolice_department(String police_department) {
        this.police_department = police_department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
