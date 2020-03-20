package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.GetOfflineTripContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class GetOfflineTripPresenter implements GetOfflineTripContract.IGetOfflineTripPresenter {
    Context context;
    public GetOfflineTripPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getOfflineTrip() {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        List<Trip> trips = roomTripModel.getOfflineTrip();
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        for (int i = 0; i < trips.size(); i++) {
            firebaseTripModel.saveTrip(trips.get(i));
        }

    }

    @Override
    public void getOfflineNoteWithSpecificTrip(String tripID) {

    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
}
