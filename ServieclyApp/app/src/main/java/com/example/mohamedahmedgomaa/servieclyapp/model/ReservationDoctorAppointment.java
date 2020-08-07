package com.example.mohamedahmedgomaa.servieclyapp.model;

public class ReservationDoctorAppointment {
    private String RDA_Specialization;
    private String RDA_Healthcare;
    private String RDA_Doctor;
    private String RDA_Schedual;
    private String RDA_Code;
    private  int RDA_id;
    public ReservationDoctorAppointment() {
    }

    public ReservationDoctorAppointment(int RDA_id,String RDA_Specialization, String RDA_Healthcare, String RDA_Doctor, String RDA_Schedual, String RDA_Code) {
        this.RDA_Specialization = RDA_Specialization;
        this.RDA_Healthcare = RDA_Healthcare;
        this.RDA_Doctor = RDA_Doctor;
        this.RDA_Schedual = RDA_Schedual;
        this.RDA_Code = RDA_Code;
        this.RDA_id=RDA_id;
    }

    public String getRDA_Specialization() {
        return RDA_Specialization;
    }

    public void setRDA_Specialization(String RDA_Specialization) {
        this.RDA_Specialization = RDA_Specialization;
    }

    public String getRDA_Healthcare() {
        return RDA_Healthcare;
    }

    public void setRDA_Healthcare(String RDA_Healthcare) {
        this.RDA_Healthcare = RDA_Healthcare;
    }

    public String getRDA_Doctor() {
        return RDA_Doctor;
    }

    public int getRDA_id() {
        return RDA_id;
    }

    public void setRDA_id(int RDA_id) {
        this.RDA_id = RDA_id;
    }

    public void setRDA_Doctor(String RDA_Doctor) {
        this.RDA_Doctor = RDA_Doctor;
    }

    public String getRDA_Schedual() {
        return RDA_Schedual;
    }

    public void setRDA_Schedual(String RDA_Schedual) {
        this.RDA_Schedual = RDA_Schedual;
    }

    public String getRDA_Code() {
        return RDA_Code;
    }

    public void setRDA_Code(String RDA_Code) {
        this.RDA_Code = RDA_Code;
    }
}
