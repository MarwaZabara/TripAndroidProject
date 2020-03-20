package com.example.tripandroidproject.POJOs;

public class Note {
    private String id;
    private String name;
    private String status;

    public Note() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
