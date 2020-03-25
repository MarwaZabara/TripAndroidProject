package com.example.tripandroidproject.View;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveUserLogIn {
    public static final String USER_DETAILS = "userDetails";

    SharedPreferences localDB;

    public SaveUserLogIn(Context context) {
        localDB = context.getSharedPreferences(USER_DETAILS, 0);
    }

    public void storeUserData(UserDetails userDetails) {
        SharedPreferences.Editor editor = localDB.edit();
        editor.putString("email", userDetails.email);
        editor.putString("password",userDetails.password);
        editor.putString("name" , userDetails.name);
        editor.putString("imgUri" ,userDetails.imgUri);
        editor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor editor = localDB.edit();
        editor.putBoolean("loggedIn", loggedIn);
        editor.commit();
    }

//    public void clearUserData() {
//        SharedPreferences.Editor editor = localDB.edit();
//        editor.clear();
//        editor.commit();
//    }

    public UserDetails getLoggedInUser() {
        if (localDB.getBoolean("loggedIn", false) == false) {
            return null;
        }
        String email = localDB.getString("email", "");
        String name = localDB.getString("name","");
        String imgUri = localDB.getString("imgUri","");
        String pass = localDB.getString("password","");
        UserDetails userDetails = new UserDetails();
        userDetails.setName(name);
        userDetails.setPassword(pass);
        userDetails.setEmail(email);
        userDetails.setImgUri(imgUri);
        return userDetails;
    }
}
