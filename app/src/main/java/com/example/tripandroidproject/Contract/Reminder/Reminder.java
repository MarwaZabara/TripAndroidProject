package com.example.tripandroidproject.Contract.Reminder;

import android.content.Context;

import com.example.tripandroidproject.Contract.Base.IBase;

import java.util.Calendar;
public class Reminder {
    public interface IReminderView extends IBase {

    }
    public interface IReminderPresenter extends IBase {
        public void startReminderService(Calendar calendar);
    }
    public interface IReminderModel {
        public void startAlarmService(Calendar calendar, int requestCode);
    }
}