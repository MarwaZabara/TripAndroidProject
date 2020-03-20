package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;

public class SaveTripPresenter implements SaveTripContract.ISaveTripPresenter {
    Context context;
    public SaveTripPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void saveTrip(Trip trip) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        if(isNetworkAvailable())
        {
            trip.setIsSync(1);
            FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
            firebaseTripModel.saveTrip(trip);
            roomTripModel.saveTrip(trip);
        }
        else {
            trip.setIsSync(0);
            roomTripModel.saveTrip(trip);
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}
