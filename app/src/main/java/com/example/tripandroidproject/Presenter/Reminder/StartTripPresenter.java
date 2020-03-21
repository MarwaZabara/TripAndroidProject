package com.example.tripandroidproject.Presenter.Reminder;

import android.content.Context;

import com.example.tripandroidproject.Contract.Reminder.Reminder;
import com.example.tripandroidproject.Model.ReminderModel.ReminderModel;

public class StartTripPresenter implements Reminder.IStartTripPresenter {
    Context context;
    ReminderModel reminderModel;
    public StartTripPresenter(Context context) {
        this.context = context;
        reminderModel = new ReminderModel(this,context);
    }

    @Override
    public void startTrip(String destinationPlaceName, String tripID,int requestCode) {

        reminderModel.startTrip(destinationPlaceName,tripID,requestCode);
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }

    public void initializeView() {
        reminderModel.initializeView();
    }
}
