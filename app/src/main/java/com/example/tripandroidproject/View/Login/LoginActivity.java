package com.example.tripandroidproject.View.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Firebase.FirebaseUserContract;
import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Contract.Room.RoomPersonContract;
import com.example.tripandroidproject.Custom.Toast.CustomToast;
import com.example.tripandroidproject.InternetConnection.CheckInternetConnection;
import com.example.tripandroidproject.Model.Room.RoomPersonModel;
import com.example.tripandroidproject.POJOs.Person;
import com.example.tripandroidproject.Presenter.Login.LoginPresenter;
import com.example.tripandroidproject.Presenter.User.FirebaseUserPresenter;
import com.example.tripandroidproject.Presenter.User.UserPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.ForgetPassword;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NavDrawer;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.View.SignUp.SignupActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity implements LoginContract.ISignInView , RoomPersonContract.IRoomPersonView , FirebaseUserContract.IUserView {

    private EditText loginEmail,loginPassword;
    private TextView forgetPass;
    private SignInButton signInButton;
    private Person person;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private LoginPresenter presenter;
    private SaveUserLogIn saveUserLogIn;
    private CheckInternetConnection checkInternetConnection;
    private static Animation shakeAnimation;
    private static RelativeLayout relativeLayout;

    private FirebaseUserPresenter firebaseUserPresenter;
    private UserPresenter userPresenter;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    ".{6,}" +               //at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        shakeAnimation = AnimationUtils.loadAnimation(this,
                R.anim.shake_anim);
        presenter = new LoginPresenter(this,this,this);
        userPresenter = new UserPresenter(this);
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
        relativeLayout = findViewById(R.id.login_layout);
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
        person = userPresenter.getUser();
            if(person!= null){
            Intent intent = new Intent(this, NavDrawer.class);
            startActivity(intent);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter = new LoginPresenter(this,this,this);
        presenter.LogInByGoogle(requestCode, resultCode, data);
    }

    private void signInGoogle() {
        if (checkInternetConnection.getConnectivityStatusString(this)){
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else {
//            Toast.makeText(this,"There is no internet connection",Toast.LENGTH_LONG).show();
            new CustomToast().Show_Toast(this, this.getCurrentFocus(),"No Internet Connection.");
        }
    }

    public void SignUP_Btn(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void LogIn_Btn(View view) {
        if (checkInternetConnection.getConnectivityStatusString(this)) {
            String email =  loginEmail.getText().toString();
            String pass =  loginPassword.getText().toString();
            if (pass.isEmpty() || email.isEmpty()){
                relativeLayout.startAnimation(shakeAnimation);
                loginEmail.setError("Email field can't be empty");
                loginPassword.setError("Password field can't be empty");
                new CustomToast().Show_Toast(this, view,"Please enter both credentials.");
            }else {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    loginEmail.setError("Please Enter valid email address");
                }else {
                    loginPassword.setError(null);
                    loginEmail.setError(null);
                    person = new Person();
                    person.setEmail(loginEmail.getText().toString());
                    person.setPassword(loginPassword.getText().toString());
                    presenter = new LoginPresenter(this, this, this);
                    presenter.onSendData(person);
                }
                }
            }else {
            new CustomToast().Show_Toast(this, view,"No Internet Connection.");
        }
    }

    @Override
    public void showMessage(Boolean result) {
        if (result){
            if (person.getName()==null){       ///////userData doesn't store in room
//                getUserData();
                firebaseUserPresenter.getUserData();   //////get from firebase
            }else  {
                Intent intent = new Intent(this, NavDrawer.class);
                intent.putExtra("Email", person.getEmail());
                intent.putExtra("Name", person.getName());
                intent.putExtra("imgUri", person.getImgUri());
                intent.putExtra("password", person.getPassword());
                startActivity(intent);
            }
        } else{
            loginPassword.setError("Please Enter valid Password");
            loginEmail.setError("Please Enter valid email address");
        }
    }


    @Override
    public Person setCurrentPerson(Person userDetails) {
        this.person = userDetails;
        return userDetails;
    }

    @Override
    public void setUserData(Person user) {
        RoomPersonModel roomPersonModel = new RoomPersonModel(this);
        person = roomPersonModel.getUser();
        if (person == null) {
            roomPersonModel.savePerson(user);
        }
        Intent intent = new Intent(this, NavDrawer.class);
        startActivity(intent);
    }

    @Override
    public void successLogin() {
        firebaseUserPresenter = new FirebaseUserPresenter(this);
        firebaseUserPresenter.getUserData();
    }
    @Override
    public void successLoginGoogle() {
        Intent intent = new Intent(this,NavDrawer.class);
        startActivity(intent);
    }
}
