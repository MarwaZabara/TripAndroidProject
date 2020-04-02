package com.example.tripandroidproject.Model.Room;

import android.content.Context;

import androidx.room.Room;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.POJOs.Trip;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RoomRepeatedTripHistoryModel {
    private final AppDatabase database;
    private final RepeatedTripHistoryDAO repeatedTripHistoryDAO;
    private ITripPresenter tripPresenter;

    public RoomRepeatedTripHistoryModel(ITripPresenter tripPresenter, Context context) {
        this.tripPresenter = tripPresenter;
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .fallbackToDestructiveMigration()
                .build();
        repeatedTripHistoryDAO = database.getRepeatedTripHistoryDAO();
    }
    public RoomRepeatedTripHistoryModel(Context context) {
        this.tripPresenter = tripPresenter;
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .fallbackToDestructiveMigration()
                .build();
        repeatedTripHistoryDAO = database.getRepeatedTripHistoryDAO();
    }
    public void saveTrip(RepeatedTripHistory repeatedTripHistory) {
        repeatedTripHistoryDAO.insert(repeatedTripHistory);
    }

    public List<RepeatedTripHistory> getRepeatedHistory() {
        return repeatedTripHistoryDAO.getTrips();
    }

    public void deleteOfflineTrip(RepeatedTripHistory repeatedTripHistory) {
        repeatedTripHistoryDAO.delete(repeatedTripHistory);
    }
}
