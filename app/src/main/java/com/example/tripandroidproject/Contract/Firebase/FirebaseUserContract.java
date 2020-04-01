package com.example.tripandroidproject.Contract.Firebase;
import com.example.tripandroidproject.POJOs.Person;

public class FirebaseUserContract {

    public interface IUserView{
//        void getUserData();
        void setUserData(Person user);
    }

    public interface IUserPresenter{
        void saveUserData(Person user);
        void getUserData();
        void updateUser(Person user);
        void onSuccess(Person user);
    }

    public interface IUserModel{
        void saveUserData(Person user);
        void updateUser(Person user);
        void getUserData();
    }

}
