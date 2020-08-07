package com.example.mohamedahmedgomaa.servieclyapp.model;

public class Post {
    private String textPost;
    private String datePost;
    private int idPost ;
    private String photoPost;
    private String usernamePost;

    public Post() {
    }

    public Post(String textPost, String datePost, int idPost, String photoPost, String usernamePost) {
        this.textPost = textPost;
        this.datePost = datePost;
        this.idPost = idPost;
        this.photoPost = photoPost;
        this.usernamePost = usernamePost;
    }

    public String getTextPost() {
        return textPost;
    }

    public void setTextPost(String textPost) {
        this.textPost = textPost;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getPhotoPost() {
        return photoPost;
    }

    public void setPhotoPost(String photoPost) {
        this.photoPost = photoPost;
    }

    public String getUsernamePost() {
        return usernamePost;
    }

    public void setUsernamePost(String usernamePost) {
        this.usernamePost = usernamePost;
    }
}
