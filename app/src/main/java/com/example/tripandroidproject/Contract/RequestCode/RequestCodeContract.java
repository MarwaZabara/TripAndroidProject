package com.example.tripandroidproject.Contract.RequestCode;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.Contract.Firebase.IFirebaseBase;
import com.example.tripandroidproject.Contract.Note.INotePresenter;
import com.example.tripandroidproject.POJOs.Note;

import java.util.List;

public class RequestCodeContract {
    public interface IRequestCodeView {
        public void retrieveRequestCode(int requestCode);
    }
    public interface IRequestCodePresenter {
        public void getRequestCode();
        public void updateRequestCode(int requestCode);
        public void onSucess(int requestCode);
    }
    public interface IRequestCodeModel {
        public void getRequestCode();
        public void updateRequestCode();
    }
}
