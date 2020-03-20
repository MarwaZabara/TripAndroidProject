package com.example.tripandroidproject.Model.ReminderModel;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.tripandroidproject.Broadcast.ReminderService.ReminderReceiver;
import com.example.tripandroidproject.Contract.Reminder.Reminder;


import java.util.Calendar;

public class ReminderModel implements Reminder.IReminderModel {
    Context context;
//    int requestCode;
    Reminder.IReminderPresenter reminderPresenter;
    public ReminderModel(Reminder.IReminderPresenter reminderPresenter, Context context) {
        this.context = context;
        this.reminderPresenter = reminderPresenter;
//        this.requestCode = requestCode;
    }
     public void startAlarmService(Calendar calendar, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

//        requestCode++;
    }
}
