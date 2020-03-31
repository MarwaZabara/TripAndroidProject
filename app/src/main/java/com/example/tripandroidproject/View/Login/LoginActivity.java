package com.example.tripandroidproject.View.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Firebase.FirebaseUserContract;
import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Contract.Room.RoomPersonContract;
import com.example.tripandroidproject.InternetConnection.CheckInternetConnection;
import com.example.tripandroidproject.Presenter.Login.LoginPresenter;
import com.example.tripandroidproject.Presenter.User.FirebaseUserPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.ForgetPassword;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NavDrawer;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.View.SignUp.SignupActivity;
import com.example.tripandroidproject.View.UserDetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;


public class LoginActivity extends AppCompatActivity implements LoginContract.ISignInView , RoomPersonContract.IRoomPersonView , FirebaseUserContract.IUserView {

    private EditText loginEmail,loginPassword;
    private TextView forgetPass;
    private SignInButton signInButton;
    private UserDetails userDetails;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private LoginPresenter presenter;
    private SaveUserLogIn saveUserLogIn;
    private CheckInternetConnection checkInternetConnection;
    private FirebaseUserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this,this,this);
        checkInternetConnection = new CheckInternetConnection();
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        forgetPass = findViewById(R.id.forgetPass);
        forgetPass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
        userPresenter = new FirebaseUserPresenter(this);
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
            Intent intent = getIntent();
            //////////////if user is coming from sign up///////////
            if (intent.getStringExtra("fromSignUp")!=null){
                userDetails.setName(intent.getStringExtra("name"));
                userDetails.setEmail(intent.getStringExtra("email"));
                userDetails.setImgUri(intent.getStringExtra("imgUri"));
                userDetails.setPassword(intent.getStringExtra("password"));
//                userDetails.setUsrImgUri(intent.getStringExtra("userImgUri"));

                userPresenter.saveUserData(userDetails);            /////////save data in firebase

                loginEmail.setText(intent.getStringExtra("email"));
                loginPassword.setText(intent.getStringExtra("password"));
                Toast.makeText(this, userDetails.getEmail(), Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(this, "no_user_Login", Toast.LENGTH_SHORT).show();
        }else {
            /////////////////////get user information to sent it to nav drawer/////////////////////////
            userDetails = saveUserLogIn.getLoggedInUser();
            presenter.onSendData(userDetails);
            if (userDetails.getName()!=null) {
                Intent intent = new Intent(this, NavDrawer.class);
                intent.putExtra("Email", userDetails.getEmail());
                intent.putExtra("Password", userDetails.getPassword());
                intent.putExtra("Name", userDetails.getName());
                intent.putExtra("imgUri", userDetails.getImgUri());
                startActivity(intent);
            }
        }
        userPresenter = new FirebaseUserPresenter(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.LogInByGoogle(requestCode, resultCode, data);
    }

    private void signInGoogle() {
        if (checkInternetConnection.getConnectivityStatusString(this)){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    public void SignUP_Btn(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void LogIn_Btn(View view) {
        if (checkInternetConnection.getConnectivityStatusString(this)) {
            userDetails.setEmail(loginEmail.getText().toString());
            userDetails.setPassword(loginPassword.getText().toString());
            presenter.onSendData(userDetails);
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(Boolean result) {
        if (result){
//            Toast.makeText(this, "signIn Success", Toast.LENGTH_SHORT).show();
            if (userDetails.getName()==null){       ///////userData doesn't store in room
//                getUserData();
                userPresenter.getUserData();   //////get from firebase
            }else  {
                Intent intent = new Intent(this, NavDrawer.class);
                intent.putExtra("Email", userDetails.getEmail());
                intent.putExtra("Name", userDetails.getName());
                intent.putExtra("imgUri", userDetails.getImgUri());
                intent.putExtra("password", userDetails.getPassword());
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "signIn Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public UserDetails setCurrentPerson(UserDetails userDetails) {
        this.userDetails = userDetails;
        return userDetails;
    }

//    @Override
//    public void getUserData() {
//
//    }

    @Override
    public void setUserData(UserDetails user) {
        this.userDetails = user;
        saveUserLogIn.storeUserData(user);
        saveUserLogIn.setUserLoggedIn(true);
        Intent intent = new Intent(this, NavDrawer.class);
        intent.putExtra("Email", userDetails.getEmail());
        intent.putExtra("Name", userDetails.getName());
        intent.putExtra("imgUri", userDetails.getImgUri());
        intent.putExtra("password", userDetails.getPassword());
        startActivity(intent);
    }
}
