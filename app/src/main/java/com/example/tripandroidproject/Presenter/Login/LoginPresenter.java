package com.example.tripandroidproject.Presenter.Login;

import android.content.Context;

import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Model.SignIn.LoginModel;
import com.example.tripandroidproject.View.UserDetails;

public class LoginPresenter implements LoginContract.ISignInPresenter {

    private LoginContract.ISignInView view;
    private LoginContract.ISignInModel model;
    private Context context;

    public LoginPresenter(LoginContract.ISignInView newView,Context context){
        this.context = context;
        view = newView;
        model = new LoginModel(context);
    }

    @Override
    public void onSendData(UserDetails userDetails) {
        Boolean result = model.signIn(userDetails);
        view.onRecieveData(result);
    }
//    @Override
//    public void LogInByGoogle(){
//        Boolean result = model.LogInByGoogle();
//        view.ResultOfGoogle(result);
//    }
}
