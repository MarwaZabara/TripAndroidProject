package com.example.tripandroidproject.Model.AddTripModel;

import android.content.Context;

import com.example.tripandroidproject.Contract.AddTrip.AddTripContract;
import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Presenter.AddTripPresenter.AddPresenter;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.google.firebase.auth.FirebaseAuth;

public class AddTripModel {
//    private static final String TAG = "State" ;
//    private FirebaseAuth mAuth;
//    private static final int RC_SIGN_IN = 9001;
    private Context context;
    private AddTripContract.IAddPresenter  presenter;
   // private SaveUserLogIn saveUserLogIn;

    public AddTripModel(Context context, AddTripContract.IAddPresenter  presenter) {
        this.context = context;
        this.presenter = presenter;
    }

//    public LoginModel(Context context, LoginContract.ISignInPresenter presenter){
//        mAuth = FirebaseAuth.getInstance();
//        this.context = context;
//        this.presenter = presenter;
//        saveUserLogIn = new SaveUserLogIn(context);
//    }
}
