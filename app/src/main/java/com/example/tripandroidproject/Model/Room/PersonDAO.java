package com.example.tripandroidproject.Model.Room;

import androidx.room.Dao;
import androidx.room.Update;

import com.example.tripandroidproject.POJOs.Person;

@Dao
public interface PersonDAO {
    @Update
    public void update(Person person);
}
