package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.Contract.Reminder.Reminder;

public class StartTripContract {
    public interface IStartTripPresenter extends IBase, Reminder.IBaseReminder {
        public void startTrip(String destinationPlaceName, String tripID,int requestCode);
        public void initializeView(String tripID);
    }
}
