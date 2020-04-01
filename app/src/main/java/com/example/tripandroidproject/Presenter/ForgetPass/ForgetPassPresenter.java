package com.example.tripandroidproject.Presenter.ForgetPass;

import android.content.Context;

import com.example.tripandroidproject.Contract.ForgetPassword.ForgetPasswordContract;
import com.example.tripandroidproject.Model.ForgetPassword.ForgetPasswordModel;

public class ForgetPassPresenter implements ForgetPasswordContract.IForgetPassPresenter {

    ForgetPasswordContract.IForgetPassModel passModel;
    ForgetPasswordContract.IForgetPassView passView;

    public ForgetPassPresenter(ForgetPasswordContract.IForgetPassView view){
        passModel = new ForgetPasswordModel(this);
        passView = view;
    }

    @Override
    public void getForgetPassword(String email) {
        passModel.getForgetPassword(email);
    }

    @Override
    public void onSucess() {
        passView.showMessage(true);
    }

    @Override
    public void onFail() {
        passView.showMessage(false);
    }
}
