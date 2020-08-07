package com.example.mohamedahmedgomaa.servieclyapp.model;

public class ComplaintModel {
    private String GobernmentBody;
    private String  SendDate;
    private String complaint ;
    private String Replay;
    private String ReplayDate;
    private int id;
    public ComplaintModel() {
    }

    public ComplaintModel(String GobernmentBody, String SendDate, String complaint, String Replay, String ReplayDate,int id) {
        this.GobernmentBody = GobernmentBody;
        this.SendDate = SendDate;
        this.Replay = Replay;
        this.ReplayDate = ReplayDate;
        this.complaint = complaint;
        this.id=id;
    }

    public String getGobernmentBody() {
        return GobernmentBody;
    }

    public void setGobernmentBody(String gobernmentBody) {
        GobernmentBody = gobernmentBody;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
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
