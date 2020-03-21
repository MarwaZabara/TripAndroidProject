package com.example.tripandroidproject.Contract.Trip;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class GetOnlineTripContract {
    public interface IGetOnlineTripPresenter extends IBase {
        public void getUpcomingTripForSync();

        public void getUpcomingNoteForSync(String tripID);
    }
    public interface IGetOnlineTripModel {
        public void getUpcomingTripForSync();

        public void getUpcomingNoteForSync(String tripID);
    }
}
