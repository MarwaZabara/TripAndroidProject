package com.example.tripandroidproject.Model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.tripandroidproject.POJOs.Note;

import java.util.List;
@Dao
public interface NoteDAO {
    @Insert
    public void insert(Note... notes);

    @Update
    public void update(Note note);

    @Delete
    public void delete(Note note);
    @Query("SELECT * FROM Note WHERE tripID == :tripID")
    public List<Note> getNotes(String tripID);
}
