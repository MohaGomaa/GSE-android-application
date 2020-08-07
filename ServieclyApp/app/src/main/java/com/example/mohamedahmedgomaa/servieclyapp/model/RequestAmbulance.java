package com.example.mohamedahmedgomaa.servieclyapp.model;

public class RequestAmbulance {
    private String Details ;
    private String Date;
    private String code;
    private double lat;
    private double lon;
    private  int id;
    public RequestAmbulance() {
    }

    public RequestAmbulance(String details, String date, String code, double lat, double lon) {
        Details = details;
        Date = date;
        this.code = code;
        this.lat = lat;
        this.lon = lon;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
