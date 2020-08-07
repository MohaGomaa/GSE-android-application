package com.example.mohamedahmedgomaa.servieclyapp.model;

public class Citizen {
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPinCard() {
        return pinCard;
    }

    public void setPinCard(String pinCard) {
        this.pinCard = pinCard;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Citizen(String nationalId, String pinCard, String qrCode) {
        this.nationalId = nationalId;
        this.pinCard = pinCard;
        this.qrCode = qrCode;
    }

    private String nationalId;
    private String pinCard;
    private String qrCode;
    private String Picture;
    private String citizen_first_name;
    private String citizen_Second_name;
    private String citizen_third_name;
    private String citizen_fourth_name;
    private String citizen_job_title;
    private String citizen_gender;
    private int citizen_id;
    private int citizen_father_id;
    private int citizen_mother_id;
    private String citizen_relegion;
    private String citizen_birthDate;
    private String citizen_birthPlace;

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getCitizen_first_name() {
        return citizen_first_name;
    }

    public Citizen(String nationalId, String pinCard, String qrCode, String picture, String citizen_first_name, String citizen_Second_name, String citizen_third_name, String citizen_fourth_name, String citizen_job_title, String citizen_gender, int citizen_id, int citizen_father_id, int citizen_mother_id, String citizen_relegion, String citizen_birthDate, String citizen_birthPlace) {
        this.nationalId = nationalId;
        this.pinCard = pinCard;
        this.qrCode = qrCode;
        Picture = picture;
        this.citizen_first_name = citizen_first_name;
        this.citizen_Second_name = citizen_Second_name;
        this.citizen_third_name = citizen_third_name;
        this.citizen_fourth_name = citizen_fourth_name;
        this.citizen_job_title = citizen_job_title;
        this.citizen_gender = citizen_gender;
        this.citizen_id = citizen_id;
        this.citizen_father_id = citizen_father_id;
        this.citizen_mother_id = citizen_mother_id;
        this.citizen_relegion = citizen_relegion;
        this.citizen_birthDate = citizen_birthDate;
        this.citizen_birthPlace = citizen_birthPlace;
    }

    public void setCitizen_first_name(String citizen_first_name) {
        this.citizen_first_name = citizen_first_name;
    }

    public String getCitizen_Second_name() {
        return citizen_Second_name;
    }

    public void setCitizen_Second_name(String citizen_Second_name) {
        this.citizen_Second_name = citizen_Second_name;
    }

    public String getCitizen_third_name() {
        return citizen_third_name;
    }

    public void setCitizen_third_name(String citizen_third_name) {
        this.citizen_third_name = citizen_third_name;
    }

    public String getCitizen_fourth_name() {
        return citizen_fourth_name;
    }

    public void setCitizen_fourth_name(String citizen_fourth_name) {
        this.citizen_fourth_name = citizen_fourth_name;
    }

    public String getCitizen_job_title() {
        return citizen_job_title;
    }

    public void setCitizen_job_title(String citizen_job_title) {
        this.citizen_job_title = citizen_job_title;
    }

    public String getCitizen_gender() {
        return citizen_gender;
    }

    public void setCitizen_gender(String citizen_gender) {
        this.citizen_gender = citizen_gender;
    }

    public int getCitizen_id() {
        return citizen_id;
    }

    public void setCitizen_id(int citizen_id) {
        this.citizen_id = citizen_id;
    }

    public int getCitizen_father_id() {
        return citizen_father_id;
    }

    public void setCitizen_father_id(int citizen_father_id) {
        this.citizen_father_id = citizen_father_id;
    }

    public int getCitizen_mother_id() {
        return citizen_mother_id;
    }

    public void setCitizen_mother_id(int citizen_mother_id) {
        this.citizen_mother_id = citizen_mother_id;
    }

    public String getCitizen_relegion() {
        return citizen_relegion;
    }

    public void setCitizen_relegion(String citizen_relegion) {
        this.citizen_relegion = citizen_relegion;
    }

    public String getCitizen_birthDate() {
        return citizen_birthDate;
    }

    public void setCitizen_birthDate(String citizen_birthDate) {
        this.citizen_birthDate = citizen_birthDate;
    }

    public String getCitizen_birthPlace() {
        return citizen_birthPlace;
    }

    public void setCitizen_birthPlace(String citizen_birthPlace) {
        this.citizen_birthPlace = citizen_birthPlace;
    }
}
