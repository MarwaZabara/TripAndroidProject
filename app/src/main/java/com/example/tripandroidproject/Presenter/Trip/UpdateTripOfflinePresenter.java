package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Contract.Trip.UpdateTripOfflineContract;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;

public class UpdateTripOfflinePresenter implements UpdateTripOfflineContract.IUpdateTripOfflinePresenter, ITripPresenter {

    UpdateTripOfflineContract.IUpdateTripOfflineModel model;
    Context context;
    public UpdateTripOfflinePresenter(Context context){
        this.context = context;
        model = new RoomTripModel(this,context);
    }

    @Override
    public void updateTrip(Trip trip) {
        model.updateTrip(trip);
    }
}
