package com.example.tripandroidproject.Presenter.User;

import com.example.tripandroidproject.Contract.Firebase.FirebaseUserContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseUserModel;
import com.example.tripandroidproject.View.UserDetails;

public class FirebaseUserPresenter implements FirebaseUserContract.IUserPresenter {

    FirebaseUserContract.IUserModel model;
    FirebaseUserContract.IUserView view;

    public FirebaseUserPresenter(FirebaseUserContract.IUserView view){
        this.view = view;
        model = new FirebaseUserModel(this);
    }

    @Override
    public void saveUserData(UserDetails user) {
        model.saveUserData(user);
    }

    @Override
    public void getUserData() {
        model.getUserData();
    }

    @Override
    public void onSuccess(UserDetails user) {
        setUserData(user);
    }

    @Override
    public void setUserData(UserDetails user) {
        view.setUserData(user);
    }

}
