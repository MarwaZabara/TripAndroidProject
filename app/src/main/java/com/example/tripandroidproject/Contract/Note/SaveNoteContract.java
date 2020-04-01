package com.example.tripandroidproject.Contract.Note;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.Contract.Firebase.IFirebaseBase;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class SaveNoteContract {
    public interface ISaveNotePresenter extends IBase, INotePresenter {
        public void saveNote(List<Note> notes, String tripid);
        public void saveNotesInRoomOnly(String tripid,List<Note> notes, String generateKey);
    }
    public interface ISaveNoteOnlineModel extends IFirebaseBase {
        public void saveNote(Note note);
        void deleteNote();
    }
    public interface ISaveNoteOfflineModel {
        public void saveNote(Note note);
    }
}
