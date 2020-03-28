package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.POJOs.Trip;

public class DeleteOfflineTripContract {

    public interface IDeleteOfflineTripPresenter {
        void deleteOfflineTrip(Trip trip);
    }

    public interface IDeleteOfflineTripModel{
        void deleteOfflineTrip(Trip trip);
    }

}
