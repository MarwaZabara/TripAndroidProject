package com.example.tripandroidproject.View;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tripandroidproject.POJOs.Person;

public class SaveUserLogIn {
    public static final String USER_DETAILS = "userDetails";

    SharedPreferences localDB;

    public SaveUserLogIn(Context context) {
        localDB = context.getSharedPreferences(USER_DETAILS, 0);
    }

//    public void storeUserData(Person userDetails) {
//        SharedPreferences.Editor editor = localDB.edit();
//        editor.putString("email", userDetails.getEmail());
//        editor.putString("password",userDetails.getPassword());
//        editor.putString("name" , userDetails.getName());
//        editor.putString("imgUri" ,userDetails.getImgUri());
//        editor.commit();
//    }

//    public void setUserLoggedIn(boolean loggedIn) {
//        SharedPreferences.Editor editor = localDB.edit();
//        editor.putBoolean("loggedIn", loggedIn);
//        editor.commit();
//    }

//    public void clearUserData() {
//        SharedPreferences.Editor editor = localDB.edit();
//        editor.clear();
//        editor.commit();
//    }

    public Person getLoggedInUser() {
        if (localDB.getBoolean("loggedIn", false) == false) {
            return null;
        }
        String email = localDB.getString("email", "");
        String name = localDB.getString("name","");
        String imgUri = localDB.getString("imgUri","");
        String pass = localDB.getString("password","");
        String imgPath = localDB.getString("imgPath","");
        Person userDetails = new Person();
        userDetails.setName(name);
        userDetails.setPassword(pass);
        userDetails.setEmail(email);
        userDetails.setImgUri(imgUri);
        return userDetails;
    }
}
