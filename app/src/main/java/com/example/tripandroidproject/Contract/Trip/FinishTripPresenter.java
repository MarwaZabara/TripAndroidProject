package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.Contract.Reminder.Reminder;

public class FinishTripPresenter {
    public interface IFinishTripPresenter extends IBase, Reminder.IBaseReminder {
        public void finishTrip(String tripID);
    }
}
