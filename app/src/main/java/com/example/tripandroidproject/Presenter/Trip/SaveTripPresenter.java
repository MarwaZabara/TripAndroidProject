package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.SaveNotePresenter;

public class SaveTripPresenter implements SaveTripContract.ISaveTripPresenter {
    Context context;
    public SaveTripPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void saveTrip(Trip trip,boolean isOfflineOnly) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);

        if(Internetonnection.isNetworkAvailable(context) && isOfflineOnly == false)
        {
            trip.setIsSync(1);
            trip.setId(firebaseTripModel.generateKey());
            firebaseTripModel.saveTrip(trip);
            roomTripModel.saveTrip(trip);


        }
        else {
            if(isOfflineOnly == false) {
                trip.setIsSync(0);
                trip.setId(firebaseTripModel.generateKey());
            }
            roomTripModel.saveTrip(trip);

        }
        if (isOfflineOnly == false && trip.getNotes().size() > 0)
        {
            SaveNotePresenter saveNotePresenter = new SaveNotePresenter(context);
            saveNotePresenter.saveNote(trip.getNotes(),trip.getId());
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }


}
