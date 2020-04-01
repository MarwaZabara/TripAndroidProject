package com.example.tripandroidproject.Model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tripandroidproject.POJOs.Person;

import java.util.List;

@Dao
public interface PersonDAO {
    @Insert
    public void insert(Person person);

    @Update
    public void update(Person person);
    @Delete
    public void delete(Person person);

    @Query("SELECT * FROM Person WHERE email = :personEmail")
    public Person getPerson(String personEmail);

    @Query("SELECT * FROM Person")
    List<Person> getAllPersons();
    @Query("SELECT * FROM Person")
    Person getUser();
}
