package com.example.tripandroidproject.Presenter.Reminder;

import android.content.Context;

import com.example.tripandroidproject.Contract.Reminder.Reminder;
import com.example.tripandroidproject.Model.ReminderModel.ReminderModel;

import java.util.Calendar;

public class ReminderPresenter implements Reminder.IReminderPresenter {
    Context context;
    public ReminderPresenter(Context context) {
        this.context = context;

    }

    @Override
    public void startReminderService(Calendar calendar, int requestCode) {
        ReminderModel reminderModel = new ReminderModel(this,context);
        reminderModel.startAlarmService(calendar,requestCode);
    }

    @Override
    public void onSucess() {
        // show message that saved success

    }

    @Override
    public void onFail() {

    }

    public void openFloatIcon(String tripID, int requestCodeHome) {
        ReminderModel reminderModel = new ReminderModel(this,context);
        reminderModel.openFloatIcon(tripID,requestCodeHome);
    }
}
