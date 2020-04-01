package com.example.tripandroidproject.Presenter.User;

import android.content.Context;

import com.example.tripandroidproject.Contract.Firebase.FirebaseUserContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseUserModel;
import com.example.tripandroidproject.Model.Room.RoomPersonModel;
import com.example.tripandroidproject.POJOs.Person;

public class FirebaseUserPresenter implements FirebaseUserContract.IUserPresenter {

    FirebaseUserContract.IUserModel model;
    FirebaseUserContract.IUserView view;

    public FirebaseUserPresenter(){
        model = new FirebaseUserModel(this);
    }
    public FirebaseUserPresenter(FirebaseUserContract.IUserView view){
        this.view = view;
        model = new FirebaseUserModel(this);
    }

    @Override
    public void saveUserData(Person user) {
        model.saveUserData(user);
    }

    @Override
    public void getUserData() {
        model.getUserData();
    }

    @Override
    public void updateUser(Person user) {
        model.updateUser(user);
    }

    @Override
    public void onSuccess(Person user) {

//        setUserData(user);
        view.setUserData(user);
    }

}
