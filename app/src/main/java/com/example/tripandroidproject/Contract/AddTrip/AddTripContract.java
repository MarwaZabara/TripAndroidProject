package com.example.tripandroidproject.Contract.AddTrip;

import android.content.Intent;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.View.UserDetails;

public class AddTripContract {

    public interface IAddView{
        void AddTripResult();
       // void showMessage(Boolean result);
    }
    public interface IAddPresenter extends IBase {
        void onSendData(UserDetails userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);
    }
    public interface IAddModel{
        void signIn(UserDetails userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);
    }
}

