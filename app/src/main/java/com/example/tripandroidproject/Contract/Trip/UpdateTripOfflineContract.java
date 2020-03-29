package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.POJOs.Trip;

public class UpdateTripOfflineContract {
    public interface IUpdateTripOfflinePresenter{
        void updateTrip(Trip trip);
    }
    public interface IUpdateTripOfflineModel{
        void updateTrip(Trip trip);
    }
}
