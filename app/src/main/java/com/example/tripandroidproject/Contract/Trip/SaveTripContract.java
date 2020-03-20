package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.Calendar;

public class SaveTripContract {
    public interface ISaveTripPresenter extends IBase {
        public void saveTrip(Trip trip);
    }
    public interface ISaveTripOnlineModel {
        public void saveTrip(Trip trip);
    }
    public interface ISaveTripOfflineModel {
        public void saveTrip(Trip trip);
    }
}
