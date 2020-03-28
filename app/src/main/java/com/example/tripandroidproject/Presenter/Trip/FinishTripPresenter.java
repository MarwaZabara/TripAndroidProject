package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.ReminderModel.ReminderModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;

public class FinishTripPresenter implements ITripPresenter, com.example.tripandroidproject.Contract.Trip.FinishTripPresenter.IFinishTripPresenter {
    Context context;
    ReminderModel reminderModel;
    public FinishTripPresenter(Context context) {
        this.context = context;
        reminderModel = new ReminderModel(this,context);
    }

    @Override
    public void finishTrip(String tripID) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        Trip trip = roomTripModel.getTripForSpecificID(tripID);
        trip.setStatus("finished");
        if(Internetonnection.isNetworkAvailable(context))
        {
            trip.setIsSync(1);
            if (trip.getRepeatEvery() > 0)
            {

            }

        }
        else {
            trip.setIsSync(0);
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
}
