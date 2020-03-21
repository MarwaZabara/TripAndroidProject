package com.example.tripandroidproject.Model.Room;

import android.content.Context;

import androidx.room.Room;

import com.example.tripandroidproject.Contract.Note.GetNoteContract;
import com.example.tripandroidproject.Contract.Note.INotePresenter;
import com.example.tripandroidproject.Contract.Note.SaveNoteContract;
import com.example.tripandroidproject.POJOs.Note;

import java.util.List;

public class RoomNoteModel implements SaveNoteContract.ISaveNoteOfflineModel, GetNoteContract.IGetNoteModel {
    private final AppDatabase database;
    private final NoteDAO noteDAO;
    private final INotePresenter notePresenter;

    public RoomNoteModel(INotePresenter notePresenter, Context context) {
        this.notePresenter = notePresenter;
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
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
}
