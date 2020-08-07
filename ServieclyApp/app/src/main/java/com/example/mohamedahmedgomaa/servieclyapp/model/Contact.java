package com.example.mohamedahmedgomaa.servieclyapp.model;

public class Contact {
private int contact_id;
private String contact_type,contact_data;

    public Contact() {
    }

    public Contact(int contact_id, String contact_type, String contact_data) {
        this.contact_id = contact_id;
        this.contact_type = contact_type;
        this.contact_data = contact_data;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getContact_data() {
        return contact_data;
    }

    public void setContact_data(String contact_data) {
        this.contact_data = contact_data;
    }


}
