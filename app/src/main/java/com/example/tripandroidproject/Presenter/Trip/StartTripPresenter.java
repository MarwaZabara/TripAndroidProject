package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Reminder.Reminder;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Contract.Trip.StartTripContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.ReminderModel.ReminderModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;

public class StartTripPresenter implements StartTripContract.IStartTripPresenter, ITripPresenter {
    Context context;
    ReminderModel reminderModel;
    public StartTripPresenter(Context context) {
        this.context = context;
        reminderModel = new ReminderModel(this,context);
    }

    @Override
    public void startTrip(String destinationPlaceName, String tripID,int requestCode) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        reminderModel.startTrip(destinationPlaceName,tripID,requestCode);
        if(Internetonnection.isNetworkAvailable(context))
        {
            Trip trip = roomTripModel.startTrip(tripID);
            trip.setIsSync(1);
            if(trip.getRepeatEvery() == 0)
                trip.setStatus("start");
            else
                trip.setStatus("repeated_Start");
            roomTripModel.updateTrip(trip);
            firebaseTripModel.updateTrip(trip);
        }
        else {
            Trip trip = roomTripModel.startTrip(tripID);
            trip.setIsSync(0);
            if(trip.getRepeatEvery() == 0)
                trip.setStatus("start");
            else
                trip.setStatus("repeated_Start");
        }



    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }

    public void initializeView(String tripID) {
        reminderModel.initializeView(tripID);
    }
}
