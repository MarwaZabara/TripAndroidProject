package com.example.tripandroidproject.POJOs;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.List;

@Entity
public class Trip { // any attribute not need set it NULL (Capital letters)
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
//    private int isFinished; // instead of boolean for firebase 1 or 0 performance better than true and false
    private String description;
    private String status; // cancel , finished , upcoming , repeated , repeated_Cancelled -- > repeat issue
    private int isRound;
    private String date; // updated if repeated
    private String time;
    private int repeatEvery; // no of days
    private String roundRepeatEvery; // no of days
    private String roundDate; // updated if repeated
    private String roundTime;
    private int requestCodeHome;
    private int requestCodeAway;
    private double startLongitude;
    private double startLatitude;
    private double endLongitude;
    private double endLatitude;
    private int isSync; // to know if trip stored in firebase or not
    @Ignore

    private List<Note> notes;
    public Trip() {
    }
    @Exclude
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

//    public int getIsFinished() {
//        return isFinished;
//    }
//
//    public void setIsFinished(int isFinished) {
//        this.isFinished = isFinished;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsRound() {
        return isRound;
    }

    public void setIsRound(int isRound) {
        this.isRound = isRound;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRepeatEvery() {
        return repeatEvery;
    }

    public void setRepeatEvery(int repeatEvery) {
        this.repeatEvery = repeatEvery;
    }

    public String getRoundRepeatEvery() {
        return roundRepeatEvery;
    }

    public void setRoundRepeatEvery(String roundRepeatEvery) {
        this.roundRepeatEvery = roundRepeatEvery;
    }

    public String getRoundDate() {
        return roundDate;
    }

    public void setRoundDate(String roundDate) {
        this.roundDate = roundDate;
    }

    public String getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(String roundTime) {
        this.roundTime = roundTime;
    }

    public int getRequestCodeHome() {
        return requestCodeHome;
    }

    public void setRequestCodeHome(int requestCodeHome) {
        this.requestCodeHome = requestCodeHome;
    }

    public int getRequestCodeAway() {
        return requestCodeAway;
    }

    public void setRequestCodeAway(int requestCodeAway) {
        this.requestCodeAway = requestCodeAway;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }
}
