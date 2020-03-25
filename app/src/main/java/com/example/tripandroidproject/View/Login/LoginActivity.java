package com.example.tripandroidproject.View.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Contract.Room.RoomPersonContract;
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


public class LoginActivity extends AppCompatActivity implements LoginContract.ISignInView , RoomPersonContract.IRoomPersonView {

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

        presenter = new LoginPresenter(this,this,this);

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        saveUserLogIn = new SaveUserLogIn(this);
        if (saveUserLogIn.getLoggedInUser() == null) {
            //////////////if user is coming from sign up///////////
            Intent intent = getIntent();
            if (intent.getStringExtra("fromSignUp")!=null){
                userDetails.setName(intent.getStringExtra("name"));
                userDetails.setEmail(intent.getStringExtra("email"));
                userDetails.setImgUri(intent.getStringExtra("imgUri"));

                loginEmail.setText(intent.getStringExtra("email"));
                loginPassword.setText(intent.getStringExtra("password"));
                Toast.makeText(this, userDetails.getEmail(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "no_user_Login", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this,NavDrawer.class);
            userDetails = saveUserLogIn.getLoggedInUser();
            presenter.onSendData(userDetails);
            intent.putExtra("Email",userDetails.getEmail());
            intent.putExtra("Name",userDetails.getName());
//            intent.putExtra("imgUri",userDetails.getImgUri());
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
            Intent intent = new Intent(this,NavDrawer.class);
            intent.putExtra("Email",userDetails.getEmail());
            intent.putExtra("Name",userDetails.getName());
            intent.putExtra("imgUri",userDetails.getImgUri());
            startActivity(intent);
        }else{

            Toast.makeText(this, "signIn Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public UserDetails setCurrentPerson(UserDetails userDetails) {
        this.userDetails = userDetails;
        return userDetails;
    }
}
