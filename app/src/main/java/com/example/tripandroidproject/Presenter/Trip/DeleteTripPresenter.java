package com.example.tripandroidproject.Presenter.Trip;

import com.example.tripandroidproject.Contract.Trip.DeleteTripContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.POJOs.Trip;

public class DeleteTripPresenter implements DeleteTripContract.IDeleteTripPresenter {

    DeleteTripContract.IDeleteTripModel model;

    public DeleteTripPresenter() {
        model = new FirebaseTripModel();
    }

    @Override
    public void deleteTrip(Trip trip) {
        model.deleteTrip(trip);
    }
}
