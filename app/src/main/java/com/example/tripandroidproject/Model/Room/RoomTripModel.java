package com.example.tripandroidproject.Model.Room;

import android.content.Context;

import androidx.room.Room;

import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.POJOs.Trip;

public class RoomTripModel implements SaveTripContract.ISaveTripOfflineModel {
    private final AppDatabase database;
    private final TripDAO tripDAO;
    private SaveTripContract.ISaveTripPresenter saveTripPresenter;

    public RoomTripModel(SaveTripContract.ISaveTripPresenter saveTripPresenter, Context context) {
        this.saveTripPresenter = saveTripPresenter;
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        tripDAO = database.getTripDAO();
    }

    @Override
    public void saveTrip(Trip trip) {
        tripDAO.insert(trip);
    }
}
