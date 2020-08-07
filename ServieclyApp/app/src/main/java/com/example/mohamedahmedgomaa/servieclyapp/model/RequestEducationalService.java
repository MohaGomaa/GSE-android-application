package com.example.mohamedahmedgomaa.servieclyapp.model;

public class RequestEducationalService {
   private String phase,language,address,requestType,id,child;
   private int quantity;
   private String date;

    public RequestEducationalService(String service, String language, String address, String date, String requestType, int quantity, String id,String child) {
        this.phase = service;
        this.language = language;
        this.address = address;
        this.date = date;
        this.requestType = requestType;
        this.quantity = quantity;
        this.id = id;
        this.child=child;
    }

    public RequestEducationalService() {
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

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
}
