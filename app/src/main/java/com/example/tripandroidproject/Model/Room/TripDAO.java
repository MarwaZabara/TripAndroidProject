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

    @Query("SELECT * FROM Trip WHERE userID = :userId")
    public List<Trip> getTrips(String userId);

    @Query("SELECT * FROM Trip WHERE isSync = 0")
    public List<Trip> getOfflineTrips();

    @Query("SELECT * FROM Trip WHERE status = :filter")
    public List<Trip> getOfflineFilteredTrips(String filter);

    @Query("SELECT * FROM Trip WHERE requestCodeHome = :requestCode OR requestCodeAway = :requestCode")
    public Trip getTripForSpecificCode(int requestCode);
    @Query("SELECT * FROM Trip WHERE id = :tripID")
    public Trip getTripForID(String tripID);
}
