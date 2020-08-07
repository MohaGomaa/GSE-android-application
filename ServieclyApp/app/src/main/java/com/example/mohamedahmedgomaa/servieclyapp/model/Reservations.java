package com.example.mohamedahmedgomaa.servieclyapp.model;

import android.text.method.DateTimeKeyListener;

import java.util.Calendar;

public class Reservations {
    private String name;
    private int reservation_id;
    private String reservation_date;
    private String reservation_office_id;
    private int reservation_citizen_id;
    private int  reservation_isDeleted;
    private String reservation_document_type_id;

    public Reservations() {
    }

    public Reservations(int reservation_id, String reservation_date, String reservation_office_id, String reservation_document_type_id) {
        this.reservation_id = reservation_id;
        this.reservation_date = reservation_date;
        this.reservation_office_id = reservation_office_id;
        this.reservation_document_type_id = reservation_document_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
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

    public String getReservation_document_type_id() {
        return reservation_document_type_id;
    }

    public void setReservation_document_type_id(String reservation_document_type_id) {
        this.reservation_document_type_id = reservation_document_type_id;
    }
}
