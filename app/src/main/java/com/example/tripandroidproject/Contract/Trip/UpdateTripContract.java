package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.POJOs.Trip;

public class UpdateTripContract {
    public interface IUpdateTripPresenter{
        void cancelTrip(Trip trip);
    }
    public interface IUpdateTripModel{
        void updateTrip(Trip trip);
    }

}
