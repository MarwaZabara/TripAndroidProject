package com.example.tripandroidproject.Presenter.User;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Model.Room.RoomPersonModel;
import com.example.tripandroidproject.POJOs.Person;

public class UserPresenter implements ITripPresenter {
    Context context;
    RoomPersonModel roomPersonModel;
    public UserPresenter(Context context) {
        this.context = context;
        roomPersonModel = new RoomPersonModel(context);
    }
    public void saveUserInRoom(Person person)
    {

        roomPersonModel.savePerson(person);
    }
    public void updateUserInRoom(Person person)
    {
        roomPersonModel.updatePerson(person);
    }

    public Person getCurrentUser(String email) {

        return roomPersonModel.getCurrentPerson(email);
    }
    public Person getUser() {

        return roomPersonModel.getUser();
    }

    public void deleteUser(Person person) {
        roomPersonModel.delete(person);
    }
}
