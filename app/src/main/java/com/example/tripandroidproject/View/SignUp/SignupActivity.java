package com.example.tripandroidproject.View.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "State" ;
    private FirebaseAuth mAuth;
    private EditText usrName,usrEmail,usrPass,usrConfirmPass;
    private ImageView usrImg;
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
        setContentView(R.layout.activity_signup);
        usrName = findViewById(R.id.userName);
        usrEmail = findViewById(R.id.userEmail);
        usrPass = findViewById(R.id.userPassword);
        usrConfirmPass = findViewById(R.id.userConfirmPass);
        usrImg = findViewById(R.id.userImage);

        mAuth = FirebaseAuth.getInstance();
    }

    public void ChooseImg(View view) {
        selectImage(this);
    }

    public void CreateAccount(View view) {
        if (ValidateName() & ValidatePassword() & ValidateConfirmePass() & ValidateEmail()){
            createAccount(usrEmail.getText().toString(), usrPass.getText().toString());
        }
    }
    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        usrImg.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                                usrImg.setImageBitmap(bitmap.createScaledBitmap(bitmap, 120, 120, false));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
    }
/////////////////////////////////////Validation//////////////////////////////////////////////////
    private boolean ValidateEmail(){
        boolean result;
        String email = usrEmail.getText().toString().trim();
        if (email.isEmpty()){
            usrEmail.setError("Field can't be empty");
            result = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            usrEmail.setError("Please Enter valid email address");
            result = false;
        } else {
            usrEmail.setError(null);
            result = true;
        }
        return result;
    }
    private boolean ValidatePassword(){
        boolean result;
        String pass = usrPass.getText().toString().trim();
        if (pass.isEmpty()){
            usrPass.setError("Field can't be empty");
            result = false;
        } else if (!PASSWORD_PATTERN.matcher(pass).matches()){
            usrPass.setError("Password too weak");
            result = false;
        } else {
            usrPass.setError(null);
            result = true;
        }
        return result;
    }
    private boolean ValidateConfirmePass(){
        boolean result;
        String confirmPass = usrConfirmPass.getText().toString().trim();
        if (confirmPass.isEmpty()) {
            usrConfirmPass.setError("Field can't be empty");
            result = false;
        } else if (!usrPass.getText().toString().matches(confirmPass)){
            usrConfirmPass.setError("Password doesn't matche");
            result = false;
        } else {
            usrConfirmPass.setError(null);
            result = true;
        }
        return result;
    }
    private boolean ValidateName(){
        boolean result;
        String name = usrName.getText().toString().trim();
        if (name.isEmpty()){
            usrName.setError("Field can't be empty");
            result = false;
        } else {
            usrName.setError(null);
            result = true;
        }
        return result;
    }
//////////////////////////////////////////////////////////////////////////////////////////////
private void createAccount(String email, String password) {
    Log.d(TAG, "createAccount:" + email);
    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(SignupActivity.this, "Create Account Successfully", Toast.LENGTH_SHORT).show();
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(SignupActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }

            // ...
        }
    });
}
}