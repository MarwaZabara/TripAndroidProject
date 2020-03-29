package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Custom.Calendar.GenerateCalendarObject;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.ReminderModel.ReminderModel;
import com.example.tripandroidproject.Model.Room.RoomRepeatedTripHistoryModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.POJOs.Trip;

import java.lang.reflect.Field;
import java.util.Calendar;

public class FinishTripPresenter implements ITripPresenter, com.example.tripandroidproject.Contract.Trip.FinishTripPresenter.IFinishTripPresenter {
    Context context;
    ReminderModel reminderModel;
    public FinishTripPresenter(Context context) {
        this.context = context;
        reminderModel = new ReminderModel(this,context);
    }

    @Override
    public void finishTrip(String tripID) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        RoomRepeatedTripHistoryModel roomRepeatedTripHistoryModel = new RoomRepeatedTripHistoryModel(this,context);
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        Trip trip = roomTripModel.getTripForSpecificID(tripID);
        RepeatedTripHistory repeatedTripHistory = new RepeatedTripHistory();
        trip.setStatus("finished");
        if(Internetonnection.isNetworkAvailable(context))
        {
            trip.setIsSync(1);
            if (trip.getRepeatEvery() > 0)
            {
                try {
                    copyObject(trip,repeatedTripHistory);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                roomRepeatedTripHistoryModel.saveTrip(repeatedTripHistory);
                Calendar calendarHome = GenerateCalendarObject.generateCalendar(trip.getDate(),"0-0");
                calendarHome.add(Calendar.DAY_OF_MONTH, trip.getRepeatEvery());
                calendarHome.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trip.getTime().split("-")[0]));
                calendarHome.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trip.getTime().split("-")[1]));
                trip.setDate(GenerateCalendarObject.generateStringDate(calendarHome));
                trip.setTime(GenerateCalendarObject.generateStringTme(calendarHome));
                

            }

        }
        else {
            trip.setIsSync(0);
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
    public static void copyObject(Object src, Object dest)
            throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException, SecurityException {
        for (Field field : src.getClass().getFields()) {
            dest.getClass().getField(field.getName()).set(dest, field.get(src));
        }
    }
}
