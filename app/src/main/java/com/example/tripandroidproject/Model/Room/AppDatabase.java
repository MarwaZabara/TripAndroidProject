package com.example.tripandroidproject.Model.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Person;
import com.example.tripandroidproject.POJOs.Trip;

@Database(entities = {Trip.class, Person.class, Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDAO getTripDAO();
    public abstract NoteDAO getNoteDAO();
    public abstract PersonDAO getPersonDAO();
    public abstract RepeatedTripHistoryDAO getRepeatedTripHistoryDAO();
}
