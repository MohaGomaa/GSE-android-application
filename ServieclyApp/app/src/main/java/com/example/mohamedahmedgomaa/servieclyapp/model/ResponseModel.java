package com.example.mohamedahmedgomaa.servieclyapp.model;

public class ResponseModel {

    private String Replay;
    private String ReplayDate;
    private int id;
    public ResponseModel() {
    }

    public ResponseModel(String Replay, String ReplayDate, int id) {

        this.Replay = Replay;
        this.ReplayDate = ReplayDate;
        this.id=id;
    }


    public String getReplay() {
        return Replay;
    }

    public void setReplay(String replay) {
        this.Replay = replay;
    }

    public String getReplayDate() {
        return ReplayDate;
    }

    public void setReplayDate(String replayDate) {
        this.ReplayDate = replayDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
