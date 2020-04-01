package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class GetOfflineTripContract {
    public interface IGetOfflineTripPresenter extends IBase,ITripPresenter {
        void getOfflineTrip();
        List<Trip> getOfflineFilteredTrip(String filter);
        List<Trip> getOfflineFilteredTrip(String filter1,String filter2);
        Trip getTripInfo(int requestCode);
        void getOfflineNoteWithSpecificTrip(String tripID);
    }
    public interface IGetOfflineTripModel {
        public List<Trip> getTrips();
        public List<Trip> getOfflineTrip();
        public List<Trip> getAllOfflineTrip();
        List<Trip> getOfflineFilteredTrip(String filter);
        List<Trip> getOfflineFilteredTrip(String filter1,String filter2);
        public Trip getTripForSpecificCode(int requsetCode);
//        public List<Note> getOfflineNote(String tripID);
    }
}
