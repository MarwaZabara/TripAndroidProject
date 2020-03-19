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

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private boolean result;
    private boolean LIgoogle;
    private Context context;

    public LoginModel(Context context){
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        result = true;
        LIgoogle = true;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    @Override
    public Boolean signIn(UserDetails userDetails) {
        if (userDetails.getEmail().matches("") | userDetails.getPassword().matches("")) {
            result = false;
            return result;
        }
        mAuth.signInWithEmailAndPassword(userDetails.getEmail(), userDetails.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            result = true;
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            result = false;
                        }
                    }
                });
        return result;
    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getEmail());
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("TAG", "signInWithCredential:success");
//                            LIgoogle = true;
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            Log.d("TAG", "signInWithCredential:failure", task.getException());
//                            LIgoogle = false;
//                        }
//                    }
//                });
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                Log.d("TAG", "Google sign in failed", e);
//            }
//        }
//    }

//    private void signInGoogle() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

//    public Boolean LogInByGoogle(){
//        return LIgoogle;
//    }

}
