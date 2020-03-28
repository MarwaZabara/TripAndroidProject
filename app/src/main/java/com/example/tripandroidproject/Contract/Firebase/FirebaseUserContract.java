package com.example.tripandroidproject.Contract.Firebase;
import com.example.tripandroidproject.View.UserDetails;

public class FirebaseUserContract {

    public interface IUserView{
        void getUserData();
        void setUserData(UserDetails user);
    }

    public interface IUserPresenter{
        void saveUserData(UserDetails user);
        void getUserData();
        void setUserData(UserDetails user);
        void onSuccess(UserDetails user);
    }

    public interface IUserModel{
        void saveUserData(UserDetails user);
        void getUserData();
    }

}
