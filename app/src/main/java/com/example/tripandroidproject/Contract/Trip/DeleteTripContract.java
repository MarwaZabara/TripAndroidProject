package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

public class DeleteTripContract {

    public interface IDeleteTripPresenter {
        void deleteTrip(Trip trip);
    }

    public interface IDeleteTripModel{
        void deleteTrip(Trip trip);
    }
}
