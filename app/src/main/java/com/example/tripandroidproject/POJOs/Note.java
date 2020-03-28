package com.example.tripandroidproject.POJOs;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey
    @NonNull
    private String id;
    private String tripID; // to escape from foreign keys
    private String name;
    private String status; // checked ,unchecked

    public Note() {
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getTripID() {
        return tripID;
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
