package com.example.tripandroidproject.Model.SignIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Presenter.Login.LoginPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.Login.LoginActivity;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.View.UserDetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

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

    public LoginModel(Context context, LoginContract.ISignInPresenter presenter){
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.presenter = presenter;
        saveUserLogIn = new SaveUserLogIn(context);
    }

    @Override
    public void signIn(final UserDetails userDetails) {
        if (userDetails.getEmail().matches("") | userDetails.getPassword().matches("")) {
            return;
        }
        mAuth.signInWithEmailAndPassword(userDetails.getEmail(), userDetails.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            presenter.onSucess();
                            saveUserLogIn.storeUserData(userDetails);
                            saveUserLogIn.setUserLoggedIn(true);
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
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

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
                UserDetails userDetails = new UserDetails();
                userDetails.setEmail(account.getEmail());
                userDetails.setName(account.getGivenName());
                userDetails.setImgUri(account.getPhotoUrl().toString());
                saveUserLogIn.storeUserData(userDetails);
                saveUserLogIn.setUserLoggedIn(true);
                presenter.onSucess();
            } catch (ApiException e) {
                Log.d("TAG", "Google sign in failed", e);
                presenter.onFail();
            }
        }
    }

}
