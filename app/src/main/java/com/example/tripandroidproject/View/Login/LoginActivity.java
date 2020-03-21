package com.example.tripandroidproject.View.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Presenter.Login.LoginPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NavDrawer;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.View.SignUp.SignupActivity;
import com.example.tripandroidproject.View.UserDetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;


public class LoginActivity extends AppCompatActivity implements LoginContract.ISignInView {

    private EditText loginEmail,loginPassword;
    private SignInButton signInButton;
    private UserDetails userDetails;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private LoginPresenter presenter;
    private SaveUserLogIn saveUserLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this,this);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        userDetails = new UserDetails();

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        saveUserLogIn = new SaveUserLogIn(this);
        if (saveUserLogIn.getLoggedInUser() == null) {
            Toast.makeText(this, "no_user_Login", Toast.LENGTH_SHORT).show();
        }else {
            userDetails = saveUserLogIn.getLoggedInUser();
            String em = userDetails.getEmail();
            String na = userDetails.getName();
            Intent intent = new Intent(this,NavDrawer.class);
            intent.putExtra("Email",userDetails.getEmail());
            intent.putExtra("Name",userDetails.getName());
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.LogInByGoogle(requestCode, resultCode, data);
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void SignUP_Btn(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void LogIn_Btn(View view) {
        userDetails.setEmail(loginEmail.getText().toString());
        userDetails.setPassword(loginPassword.getText().toString());
        presenter.onSendData(userDetails);
    }

    @Override

    public void showMessage(Boolean result) {
        if (result){
            Toast.makeText(this, "signIn Success", Toast.LENGTH_SHORT).show();
//            Intent loginIntent = new Intent(this, NavDrawer.class);
//            loginIntent.putExtra("Email",userDetails.getEmail());
//            loginIntent.putExtra("Name",userDetails.getName());
//            startActivity(loginIntent);
            userDetails = saveUserLogIn.getLoggedInUser();
            String em = userDetails.getEmail();
            String na = userDetails.getName();
            Intent intent = new Intent(this,NavDrawer.class);
            intent.putExtra("Email",userDetails.getEmail());
            intent.putExtra("Name",userDetails.getName());
            startActivity(intent);
        }else{

            Toast.makeText(this, "signIn Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
