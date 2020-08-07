package com.example.mohamedahmedgomaa.servieclyapp.model;

import java.io.File;

public class StudentCertification {
    private String Certifcation_Name;
    private String date;
    private String GradeName;

    public StudentCertification() {
    }

    public StudentCertification(String certifcation_Name, String date, String gradeName) {
        Certifcation_Name = certifcation_Name;
        this.date = date;
        GradeName = gradeName;
    }

    public String getCertifcation_Name() {
        return Certifcation_Name;
    }

    public void setCertifcation_Name(String certifcation_Name) {
        Certifcation_Name = certifcation_Name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String gradeName) {
        GradeName = gradeName;
    }
}
