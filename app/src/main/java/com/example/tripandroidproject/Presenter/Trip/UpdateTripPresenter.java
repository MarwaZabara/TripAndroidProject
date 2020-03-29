package com.example.tripandroidproject.Presenter.Trip;

import com.example.tripandroidproject.Contract.Trip.UpdateTripContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.POJOs.Trip;

public class UpdateTripPresenter implements UpdateTripContract.IUpdateTripPresenter {

    UpdateTripContract.IUpdateTripModel model;

    public UpdateTripPresenter() {
        model = new FirebaseTripModel();
    }

    @Override
    public void updateTrip(Trip trip) {
        model.updateTrip(trip);
    }
}
