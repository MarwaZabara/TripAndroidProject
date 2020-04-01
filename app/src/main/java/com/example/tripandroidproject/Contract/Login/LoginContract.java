package com.example.tripandroidproject.Contract.Login;

import android.content.Intent;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Person;

public class LoginContract {
    public interface ISignInView{
        void showMessage(Boolean result);

        void successLogin();

        void successLoginGoogle();
    }
    public interface ISignInPresenter extends IBase {
        void onSendData(Person userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);

        void onSucessLogin();
    }
    public interface ISignInModel{
        void signIn(Person userDetails);
        void LogInByGoogle(int requestCode, int resultCode, Intent data);
    }
}

