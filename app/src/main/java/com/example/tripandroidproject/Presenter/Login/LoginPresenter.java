package com.example.tripandroidproject.Presenter.Login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Model.SignIn.LoginModel;
import com.example.tripandroidproject.View.UserDetails;

public class LoginPresenter implements LoginContract.ISignInPresenter {

    private LoginContract.ISignInView view;
    private LoginContract.ISignInModel model;
    private Context context;
    private Boolean result;

    public LoginPresenter(LoginContract.ISignInView newView,Context context){
        this.context = context;
        view = newView;
        model = new LoginModel(context,this);
    }

    @Override
    public void onSendData(UserDetails userDetails) {
        model.signIn(userDetails);
    }

    @Override
    public void onSucess() {
        result = true;
        Log.d("TAG","Success");
        view.showMessage(result);
    }

    @Override
    public void onFail() {
        result = false;
        Log.d("TAG","Fail");
        view.showMessage(result);
    }
    @Override
    public void LogInByGoogle(int requestCode, int resultCode, Intent data){
        model.LogInByGoogle(requestCode, resultCode, data);
    }
}
