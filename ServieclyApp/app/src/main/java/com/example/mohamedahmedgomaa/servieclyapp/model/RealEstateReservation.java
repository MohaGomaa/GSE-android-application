package com.example.mohamedahmedgomaa.servieclyapp.model;

public class RealEstateReservation {
    private String name;
    private String reservation_id;
    private String reservation_date;
    private String reservation_office_id;
    private int reservation_citizen_id;
    private int  reservation_isDeleted;
    private String reservation_service_id;

    public RealEstateReservation() {
    }

    public RealEstateReservation(String reservation_id, String reservation_date, String reservation_office_id, String reservation_service_id) {
        this.reservation_id = reservation_id;
        this.reservation_date = reservation_date;
        this.reservation_office_id = reservation_office_id;
        this.reservation_service_id = reservation_service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public String getReservation_office_id() {
        return reservation_office_id;
    }

    public void setReservation_office_id(String reservation_office_id) {
        this.reservation_office_id = reservation_office_id;
    }

    public int getReservation_citizen_id() {
        return reservation_citizen_id;
    }

    public void setReservation_citizen_id(int reservation_citizen_id) {
        this.reservation_citizen_id = reservation_citizen_id;
    }

    public int getReservation_isDeleted() {
        return reservation_isDeleted;
    }

    public void setReservation_isDeleted(int reservation_isDeleted) {
        this.reservation_isDeleted = reservation_isDeleted;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getReservation_service_id() {
        return reservation_service_id;
    }

    public void setReservation_service_id(String reservation_service_id) {
        this.reservation_service_id = reservation_service_id;
    }

    public String getReservation_id() {
        return reservation_id;
    }
}
