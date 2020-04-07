package com.example.tripandroidproject.Model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

@Dao
public interface RepeatedTripHistoryDAO {
    @Insert
    public void insert(RepeatedTripHistory repeatedTripHistorie);
    @Delete
    public void delete(RepeatedTripHistory repeatedTripHistorie);
    @Query("SELECT * FROM RepeatedTripHistory")
    public List<RepeatedTripHistory> getTrips();
    @Query("SELECT * FROM RepeatedTripHistory WHERE isSync = 0")
    public List<RepeatedTripHistory> getRepeatedHistoryNotSync();
}
