package com.example.tripandroidproject.Contract.AddTrip;

import android.content.Intent;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Person;

public class AddTripContract {

    public interface IAddView{
        void AddTripResult();
       // void showMessage(Boolean result);
    }
    public interface IAddPresenter extends IBase {
        void onSendData(Person userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);
    }
    public interface IAddModel{
        void signIn(Person userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);
    }
}

