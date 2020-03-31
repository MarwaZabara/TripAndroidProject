package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Reminder.Reminder;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Contract.Trip.UpdateTripContract;
import com.example.tripandroidproject.Custom.Calendar.GenerateCalendarObject;
import com.example.tripandroidproject.Model.Firebase.FirebaseRepeatedTripHistory;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.ReminderModel.ReminderModel;
import com.example.tripandroidproject.Model.Room.RoomRepeatedTripHistoryModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.Calendar;

public class UpdateTripPresenter implements ITripPresenter, UpdateTripContract.IUpdateTripPresenter, Reminder.IBaseReminder {

    UpdateTripContract.IUpdateTripModel model;
    Context context;
    public UpdateTripPresenter(Context context) {
        model = new FirebaseTripModel();
        this.context = context;
    }

    @Override
    public void updateTrip(Trip trip) {
//        model.updateTrip(trip);
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        RoomRepeatedTripHistoryModel roomRepeatedTripHistoryModel = new RoomRepeatedTripHistoryModel(this,context);
        ReminderModel reminderModel = new ReminderModel(this,context);

        FirebaseRepeatedTripHistory firebaseRepeatedTripHistory = new FirebaseRepeatedTripHistory();
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel();

        if (trip.getRepeatEvery() > 0)
        {
            RepeatedTripHistory repeatedTripHistory = setObject(trip);
            trip.setStatus("repeated");
            repeatedTripHistory.setStatus("repeated_Cancelled");
            if(Internetonnection.isNetworkAvailable(context))
            {
                trip.setIsSync(1);
                repeatedTripHistory.setIsSync(1);
                repeatedTripHistory.setId(firebaseRepeatedTripHistory.generateKey());
                firebaseRepeatedTripHistory.saveTrip(repeatedTripHistory);
            }
            else {
                trip.setIsSync(0);
                repeatedTripHistory.setIsSync(0);
                roomRepeatedTripHistoryModel.saveTrip(repeatedTripHistory);
            }
            Calendar calendarHome = GenerateCalendarObject.generateCalendar(trip.getDate(),"0-0");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, GenerateCalendarObject.dayOfYear + (int) trip.getRepeatEvery());
            trip.setDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + String.valueOf(calendar.get(Calendar.YEAR)));
            reminderModel.startAlarmService(GenerateCalendarObject.generateCalendar(trip.getDate(),trip.getTime()),trip.getRequestCodeHome());
        }
        else {
            trip.setStatus("Cancel");
        }
        if(Internetonnection.isNetworkAvailable(context))
        {
            trip.setIsSync(1);
            firebaseTripModel.saveTrip(trip);
        }
        else {
            trip.setIsSync(0);
        }
        reminderModel.stopAlarmService(trip.getRequestCodeHome());
        roomTripModel.updateTrip(trip);
        reminderModel.stopService();
    }
    private RepeatedTripHistory setObject(Trip trip) {
        RepeatedTripHistory repeatedTripHistory = new RepeatedTripHistory(trip.getId(), trip.getUserID(), trip.getName(),
                trip.getDescription(), "finished", trip.getDate(), trip.getTime(),
                trip.getRepeatEvery(), trip.getRequestCodeHome(), trip.getStartLongitude(),
                trip.getStartLatitude(), trip.getEndLongitude(), trip.getEndLatitude(), trip.getIsSync(),
                trip.getNotes());
        return repeatedTripHistory;
    }
}
