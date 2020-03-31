package com.example.tripandroidproject.Model.ForgetPassword;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tripandroidproject.Contract.ForgetPassword.ForgetPasswordContract;
import com.example.tripandroidproject.Presenter.ForgetPass.ForgetPassPresenter;
import com.example.tripandroidproject.View.ForgetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordModel implements ForgetPasswordContract.IForgetPassModel {

    private FirebaseAuth mAuth;
    private ForgetPasswordContract.IForgetPassPresenter passPresenter;

    public ForgetPasswordModel(ForgetPasswordContract.IForgetPassPresenter passPresenter){
        mAuth = FirebaseAuth.getInstance();
        this.passPresenter = passPresenter;
    }

    @Override
    public void getForgetPassword(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    passPresenter.onSucess();
                }else{
                    passPresenter.onFail();
                }
            }
        });
    }
}
