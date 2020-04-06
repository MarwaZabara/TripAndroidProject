package com.example.tripandroidproject.Model.SignUp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tripandroidproject.Contract.SignUp.SignUpContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseUserModel;
import com.example.tripandroidproject.Model.Room.RoomPersonModel;
import com.example.tripandroidproject.POJOs.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpModel implements SignUpContract.ISignUpModel {

    private static final String TAG = "State" ;
    private FirebaseAuth mAuth;
    private Context context;
    private SignUpContract.ISignUpPresenter presenter;
    private RoomPersonModel roomPersonModel;

    public SignUpModel(Context context, SignUpContract.ISignUpPresenter presenter){
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.presenter = presenter;
        roomPersonModel = new RoomPersonModel(this.context);
    }
    @Override
    public void signUp(final Person userDetails) {
        Log.d(TAG, "createAccount:" + userDetails.getEmail());
        mAuth.createUserWithEmailAndPassword(userDetails.getEmail(), userDetails.getPassword()).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
//                    mAuth = FirebaseAuth.getInstance();
                    roomPersonModel.savePerson(userDetails);
                    FirebaseUserModel firebaseUserModel = new FirebaseUserModel();
                    firebaseUserModel.saveUserData(userDetails);
//                    presenter.onSucess();
                    presenter.onSucess();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    presenter.onFail();
                }

                // ...
            }
        });
    }
}
