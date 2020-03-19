package com.example.tripandroidproject.View.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Login.LoginContract;
import com.example.tripandroidproject.Presenter.Login.LoginPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.SignUp.SignupActivity;
import com.example.tripandroidproject.View.UserDetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity implements LoginContract.ISignInView {

    private static final String TAG = "State" ;
    private EditText loginEmail,loginPassword;
    private FirebaseAuth mAuth;
    private SignInButton signInButton;
    private UserDetails userDetails;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private LoginPresenter presenter;

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
//                presenter.LogInByGoogle();
                signInGoogle();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

//    private void signIn(String email, String password) {
//        if (!ValidateEmail() & !ValidatePassword()) {
//            return;
//        }
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(LoginActivity.this, "signIn Successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(LoginActivity.this, "signInWithGoogle:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
//                            intent.putExtra("email", email.getText().toString());
//                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "signInWithGoogle:failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

//        signIn(loginEmail.getText().toString(),loginPassword.getText().toString());
    }
    /////////////////////////////////////Validation//////////////////////////////////////////////////
    private boolean ValidateEmail(){
        boolean result;
        String email = loginEmail.getText().toString().trim();
        if (email.isEmpty()){
            loginEmail.setError("Field can't be empty");
            result = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.setError("Please Enter valid email address");
            result = false;
        } else {
            loginEmail.setError(null);
            result = true;
        }
        return result;
    }
    private boolean ValidatePassword(){
        boolean result;
        String pass = loginPassword.getText().toString().trim();
        if (pass.isEmpty()){
            loginPassword.setError("Field can't be empty");
            result = false;
        } else {
            loginPassword.setError(null);
            result = true;
        }
        return result;
    }

    @Override
    public void onRecieveData(Boolean data) {
        if (data){
            Toast.makeText(this, "signIn Successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "signIn Failed", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void ResultOfGoogle(Boolean data){
//        if (data){
//            Toast.makeText(this, "signIn Successfully", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "signIn Failed", Toast.LENGTH_SHORT).show();
//        }
//    }
}
