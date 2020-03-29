package com.example.tripandroidproject.Contract.Reminder;

import com.example.tripandroidproject.Contract.Base.IBase;

import java.util.Calendar;
public class Reminder {
    public interface IBaseReminder {

    }
    public interface IReminderView extends IBase {

    }
    public interface IReminderPresenter extends IBase,IBaseReminder {
        public void startReminderService(Calendar calendar, int requestCode);
    }
    public interface IReminderModel {
        public void startAlarmService(Calendar calendar, int requestCode);
        public void startTrip(String destinationPlaceName, String tripID,int requestCode);
        public void initializeView(String tripID);
    }


}