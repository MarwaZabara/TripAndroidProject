package com.example.tripandroidproject.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.ForgetPassword.ForgetPasswordContract;
import com.example.tripandroidproject.Presenter.ForgetPass.ForgetPassPresenter;
import com.example.tripandroidproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.r0adkll.slidr.model.SlidrInterface;

public class ForgetPassword extends AppCompatActivity implements ForgetPasswordContract.IForgetPassView {

    ForgetPasswordContract.IForgetPassPresenter passPresenter;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        passPresenter = new ForgetPassPresenter(this);

        final EditText email = findViewById(R.id.email);
        Button btn = findViewById(R.id.btnGet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                passPresenter.getForgetPassword(e);
            }
        });
    }

    @Override
    public void showMessage(boolean result) {
        if (result){
            Toast.makeText(ForgetPassword.this, "Link to reset your password sent to your email", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ForgetPassword.this, "Please make sure that you enter correct email", Toast.LENGTH_SHORT).show();
        }
    }
}
