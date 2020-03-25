package com.example.tripandroidproject.Model.Firebase;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUserModel {
    public static String getUserID()
    {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
