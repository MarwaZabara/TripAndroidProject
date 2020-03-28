package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.DeleteOfflineTripContract;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;

public class DeleteOfflineTripPresenter implements DeleteOfflineTripContract.IDeleteOfflineTripPresenter {

    Context context;

    public DeleteOfflineTripPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void deleteOfflineTrip(Trip trip) {
        RoomTripModel roomTripModel = new RoomTripModel(context);
        roomTripModel.deleteOfflineTrip(trip);
    }
}
