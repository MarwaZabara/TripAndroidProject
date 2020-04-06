package com.example.tripandroidproject.Presenter.SignUp;

import android.content.Context;
import android.util.Log;

import com.example.tripandroidproject.Contract.SignUp.SignUpContract;
import com.example.tripandroidproject.Model.SignUp.SignUpModel;
import com.example.tripandroidproject.POJOs.Person;

public class SignUpPresenter implements SignUpContract.ISignUpPresenter {

    private static final String TAG = "State" ;
    private Context context;
    private SignUpContract.ISignUpView view;
    private SignUpContract.ISignUpModel model;
    private Boolean result;

    public SignUpPresenter(SignUpContract.ISignUpView newView, Context context){
        this.context = context;
        view = newView;
    }
    @Override
    public void onSendData(Person userDetails) {
        model = new SignUpModel(context,this);
        model.signUp(userDetails);
    }

    @Override
    public void onSucess() {
        result = true;
        Log.d(TAG,"Success");
        view.showMessage(result);

    }

    @Override
    public void onFail() {
        result = false;
        Log.d(TAG,"Fail");
        view.showMessage(result);
    }
}
