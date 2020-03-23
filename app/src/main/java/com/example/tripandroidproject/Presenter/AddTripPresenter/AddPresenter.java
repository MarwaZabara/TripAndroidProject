package com.example.tripandroidproject.Presenter.AddTripPresenter;

import android.content.Context;

import com.example.tripandroidproject.Contract.AddTrip.AddTripContract;
import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Model.AddTripModel.AddTripModel;
import com.example.tripandroidproject.Model.SignIn.LoginModel;

public class AddPresenter {
    private AddTripContract.IAddModel model;
    private  AddTripContract.IAddView view;
    private Context context;
   // private Boolean result;

//    public LoginPresenter(LoginContract.ISignInView newView,Context context){
//        this.context = context;
//        view = newView;
//        model = new LoginModel(context,this);
//    }

    public AddPresenter(AddTripContract.IAddView newView, Context context){
        this.context = context;
        view = newView;
       // model = new AddTripModel(context,this);
    }

}
