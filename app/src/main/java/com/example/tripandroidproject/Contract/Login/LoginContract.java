package com.example.tripandroidproject.Contract.Login;

import android.content.Intent;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.View.UserDetails;

public class LoginContract {
    public interface ISignInView{
        void showMessage(Boolean result);
    }
    public interface ISignInPresenter extends IBase {
        void onSendData(UserDetails userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);
    }
    public interface ISignInModel{
        void signIn(UserDetails userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);
    }
}

