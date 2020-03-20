package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.Calendar;
import java.util.List;

public class SaveTripContract {
    public interface ISaveTripPresenter extends IBase,ITripPresenter {
        public void saveTrip(Trip trip);
    }
    public interface ISaveTripOnlineModel {
        public void saveTrip(Trip trip);
        public String generateKey();
    }
    public interface ISaveTripOfflineModel {
        public void saveTrip(Trip trip);

    }
}
