package com.example.tripandroidproject.Model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

@Dao
public interface TripDAO {
    @Insert
    public void insert(Trip... trips);

    @Update
    public void update(Trip trip);

    @Delete
    public void delete(Trip trip);
    @Query("SELECT * FROM Trip")
    public List<Trip> getTrips();
    @Query("SELECT * FROM Trip WHERE isSync = 0")
    public List<Trip> getOfflineTrips();


}
