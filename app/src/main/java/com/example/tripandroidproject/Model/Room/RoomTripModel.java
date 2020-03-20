package com.example.tripandroidproject.Model.Room;

import android.content.Context;

import androidx.room.Room;

import com.example.tripandroidproject.Contract.Trip.GetOfflineTripContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class RoomTripModel implements SaveTripContract.ISaveTripOfflineModel , GetOfflineTripContract.IGetOfflineTripModel {
    private final AppDatabase database;
    private final TripDAO tripDAO;
    private ITripPresenter tripPresenter;

    public RoomTripModel(ITripPresenter tripPresenter, Context context) {
        this.tripPresenter = tripPresenter;
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        tripDAO = database.getTripDAO();
    }

    @Override
    public void saveTrip(Trip trip) {
        tripDAO.insert(trip);
    }

    @Override
    public List<Trip> getOfflineTrip() {
        return tripDAO.getOfflineTrips();
    }
}
