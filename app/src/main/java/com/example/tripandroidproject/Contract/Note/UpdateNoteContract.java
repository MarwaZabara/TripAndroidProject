package com.example.tripandroidproject.Contract.Note;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.Contract.Firebase.IFirebaseBase;
import com.example.tripandroidproject.POJOs.Note;

import java.util.List;

public class UpdateNoteContract  {
    public interface IUpdateNotePresenter extends INotePresenter {
        public void updateNote(Note note);
    }
    public interface IUpdateNoteOnlineModel extends IFirebaseBase {
        public void updateNote(Note note);
    }
    public interface IUpdateNoteOfflineModel {
        public void updateNote(Note note);
    }
}
