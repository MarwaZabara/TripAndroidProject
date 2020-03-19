package com.example.tripandroidproject.Contract.Login;

import android.content.Intent;

import com.example.tripandroidproject.View.UserDetails;

public class LoginContract {
    public interface ISignInView{
        void onRecieveData(Boolean data);
//        void ResultOfGoogle(Boolean data);
    }
    public interface ISignInPresenter{
        void onSendData(UserDetails userDetails);
//        void LogInByGoogle();
    }
    public interface ISignInModel{
        Boolean signIn(UserDetails userDetails);
//        Boolean LogInByGoogle();
    }
}

