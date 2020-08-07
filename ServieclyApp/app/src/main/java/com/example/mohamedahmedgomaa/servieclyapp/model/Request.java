package com.example.mohamedahmedgomaa.servieclyapp.model;

import java.time.LocalDateTime;

public class Request {
   private String service,language,address,requestType;
   private int quantity,id;
   private String date;
    public Request(String service, String language, String address, String date, String requestType, int quantity, int id) {
        this.service = service;
        this.language = language;
        this.address = address;
        this.date = date;
        this.requestType = requestType;
        this.quantity = quantity;
        this.id = id;
    }

    public Request() {
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
