package com.example.tripandroidproject.Contract.Note;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class GetNoteContract {
    public interface IGetNotePresenter extends IBase, INotePresenter {
        public List<Note> getNotes(String tripID);
    }
    public interface IGetNoteModel {
//        public List<Trip> getOfflineTrip();
        public List<Note> getNotes(String tripID);
    }
}
