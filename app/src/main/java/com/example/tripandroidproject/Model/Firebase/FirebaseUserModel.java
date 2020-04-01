package com.example.tripandroidproject.Model.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tripandroidproject.Contract.Firebase.FirebaseUserContract;
import com.example.tripandroidproject.Model.SignUp.SignUpModel;
import com.example.tripandroidproject.POJOs.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUserModel implements FirebaseUserContract.IUserModel{

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    FirebaseUserContract.IUserPresenter userPresenter;

    public FirebaseUserModel() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        myRef = database.getReference("User").child(userID);
    }


    public static String getUserID()
    {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public FirebaseUserModel(FirebaseUserContract.IUserPresenter userPresenter){
        this.userPresenter = userPresenter;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        if (mAuth.getCurrentUser()!=null) {
            String userID = mAuth.getCurrentUser().getUid();
            myRef = database.getReference("User").child(userID);
        }
    }

    @Override
    public void saveUserData(Person person) {
        myRef.setValue(person);
    }

    @Override
    public void updateUser(Person user) {
        String userID = mAuth.getCurrentUser().getUid();
        myRef = database.getReference("User").child(userID);
        myRef.setValue(user);
    }

    @Override
    public void getUserData() {
        String userID = mAuth.getCurrentUser().getUid();
        myRef = database.getReference("User").child(userID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    Person userDetails = new Person();
                    userDetails.setName(dataSnapshot.getValue(Person.class).getName());
                    userDetails.setPassword(dataSnapshot.getValue(Person.class).getPassword());
                    userDetails.setEmail(dataSnapshot.getValue(Person.class).getEmail());
                    userDetails.setImgUri(dataSnapshot.getValue(Person.class).getImgUri());
                    userDetails.setFirebasePhotoPath(dataSnapshot.getValue(Person.class).getFirebasePhotoPath());
                    userPresenter.onSuccess(userDetails);
                    Log.d("TAG", userDetails.getName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}
