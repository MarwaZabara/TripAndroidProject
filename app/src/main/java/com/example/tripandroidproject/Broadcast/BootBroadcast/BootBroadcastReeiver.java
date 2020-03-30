package com.example.tripandroidproject.Broadcast.BootBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tripandroidproject.Custom.Calendar.GenerateCalendarObject;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Reminder.ReminderPresenter;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;

import java.util.Calendar;
import java.util.List;

public class BootBroadcastReeiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i("AfterReboot","Done");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                GetOfflineTripPresenter getOfflineTripPresenter = new GetOfflineTripPresenter(context);
                List<Trip> trips = getOfflineTripPresenter.getTrips();
                ReminderPresenter reminderPresenter = new ReminderPresenter(context);
                for(int i = 0; i < trips.size(); i++)
                {
                    Calendar calendar = GenerateCalendarObject.generateCalendar(trips.get(i).getDate(), trips.get(i).getTime());
                    reminderPresenter.startReminderService(calendar, trips.get(i).getRequestCodeHome());
                    Log.i("AfterReboot","Save"+trips.get(i).getName());
                    /*if(trips.get(i).getIsRound() == 1) {
                        calendar = GenerateCalendarObject.generateCalendar(trips.get(i).getRoundDate(), trips.get(i).getRoundTime());
                        reminderPresenter.startReminderService(calendar, trips.get(i).getRequestCodeAway());
                        Log.i("AfterReboot","Save Round "+trips.get(i).getName());
                    }*/

                }

            }
        });
        th.start();
//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
//
//
//        }
    }
}
