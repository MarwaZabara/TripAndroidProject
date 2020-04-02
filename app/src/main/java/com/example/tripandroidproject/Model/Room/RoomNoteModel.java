package com.example.tripandroidproject.Model.Room;

import android.content.Context;

import androidx.room.Room;

import com.example.tripandroidproject.Contract.Note.GetNoteContract;
import com.example.tripandroidproject.Contract.Note.INotePresenter;
import com.example.tripandroidproject.Contract.Note.SaveNoteContract;
import com.example.tripandroidproject.Contract.Note.UpdateNoteContract;
import com.example.tripandroidproject.POJOs.Note;

import java.util.List;

public class RoomNoteModel implements SaveNoteContract.ISaveNoteOfflineModel, GetNoteContract.IGetNoteModel, UpdateNoteContract.IUpdateNoteOfflineModel {
    private final AppDatabase database;
    private final NoteDAO noteDAO;

    public RoomNoteModel(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .fallbackToDestructiveMigration()
                .build();
        noteDAO = database.getNoteDAO();
    }

    @Override
    public void saveNote(Note note) {
        noteDAO.insert(note);
    }

    @Override
    public List<Note> getNotes(String tripID) {
        return noteDAO.getNotes(tripID);
    }


    @Override
    public void updateNote(Note note) {
        noteDAO.update(note);
    }
    public void deleteNote(Note note) {
        noteDAO.delete(note);
    }
}
