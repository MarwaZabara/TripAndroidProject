package com.example.tripandroidproject.Model.LogIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Contract.Room.RoomPersonContract;
import com.example.tripandroidproject.Model.Room.RoomPersonModel;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.View.UserDetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginModel implements LoginContract.ISignInModel {

    private static final String TAG = "State" ;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private Context context;
    private LoginContract.ISignInPresenter presenter;
    private SaveUserLogIn saveUserLogIn;
    private RoomPersonModel roomPersonModel;
    private RoomPersonContract.IRoomPersonPresenter personPresenter;

    public LoginModel(Context context, LoginContract.ISignInPresenter presenter,RoomPersonContract.IRoomPersonPresenter personPresenter){
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.presenter = presenter;
        this.personPresenter = personPresenter;
        saveUserLogIn = new SaveUserLogIn(context);
        roomPersonModel = new RoomPersonModel(this.context);
    }

    @Override
    public void signIn(final UserDetails userDetails) {
        if (userDetails.getEmail().matches("") | userDetails.getPassword().matches("")) {
            Log.d(TAG,"error from sign in");
            return;
        }
        mAuth.signInWithEmailAndPassword(userDetails.getEmail(), userDetails.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserDetails user = roomPersonModel.getCurrentPerson(userDetails.getEmail());
                            personPresenter.setCurrentPerson(user);
                            presenter.onSucess();
                            if (user.getName()!=null){
                                saveUserLogIn.storeUserData(user);
                                saveUserLogIn.setUserLoggedIn(true);
                            }

                        } else {
                            Log.d("TAG", "signInWithEmail:failure", task.getException());
                            presenter.onFail();
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getEmail());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserDetails userDetails = new UserDetails();
                            userDetails.setEmail(acct.getEmail());
                            userDetails.setName(acct.getGivenName());
                            userDetails.setImgUri(acct.getPhotoUrl().toString());

                            //// in case of sign in by gmail user needs internet cann't sign in offline

                            personPresenter.setCurrentPerson(userDetails);
                            saveUserLogIn.storeUserData(userDetails);
                            saveUserLogIn.setUserLoggedIn(true);
                            presenter.onSucess();
                            Log.d("TAG", "signInWithCredential:success");
                        } else {
                            Log.d("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void LogInByGoogle(int requestCode, int resultCode, Intent data){
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);


            } catch (ApiException e) {
                Log.d("TAG", "Google sign in failed", e);
                presenter.onFail();
            }
        }
    }

}
