package com.example.tripandroidproject.View.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.example.tripandroidproject.Contract.SignUp.SignUpContract;
import com.example.tripandroidproject.InternetConnection.CheckInternetConnection;
import com.example.tripandroidproject.Presenter.Login.LoginPresenter;
import com.example.tripandroidproject.Presenter.SignUp.SignUpPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.Login.LoginActivity;
import com.example.tripandroidproject.View.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements SignUpContract.ISignUpView {
    FirebaseStorage storage;
    StorageReference storageReference;
    private CheckInternetConnection checkInternetConnection;
    private UserDetails userDetails;
    private SignUpPresenter presenter;
    private EditText usrName,usrEmail,usrPass,usrConfirmPass;
    private ImageView usrImg;
    private String usrImgUri = "";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    ".{6,}" +               //at least 6 characters
                    "$");
    private Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        usrName = findViewById(R.id.userName);
        usrEmail = findViewById(R.id.userEmail);
        usrPass = findViewById(R.id.userPassword);
        usrConfirmPass = findViewById(R.id.userConfirmPass);
        usrImg = findViewById(R.id.userImage);

        userDetails = new UserDetails();
        presenter = new SignUpPresenter(this,this);
        checkInternetConnection = new CheckInternetConnection();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public void ChooseImg(View view) {
        selectImage(this);
    }

    public void CreateAccount(View view) {
        if (checkInternetConnection.getConnectivityStatusString(this)) {
            if (ValidateName() & ValidatePassword() & ValidateConfirmePass() & ValidateEmail()) {
                userDetails.setEmail(usrEmail.getText().toString());
                userDetails.setPassword(usrPass.getText().toString());
                userDetails.setName(usrName.getText().toString());
                userDetails.setImgUri(usrImgUri);
                presenter.onSendData(userDetails);
                uploadImage();
            }else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
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
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                    pickPhoto.setType("image/*");
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
                        Bitmap selectedImage1 = (Bitmap) data.getExtras().get("data");
                        usrImg.setImageBitmap(selectedImage1);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null && data.getData()!=null) {
                        selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {

                            try {
                                usrImgUri = selectedImage.toString();
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                                usrImg.setImageBitmap(bitmap.createScaledBitmap(bitmap, 120, 120, false));
//                                ////////////////////////////////////////////////////////////////////////////
//                                Cursor cursor = getContentResolver().query(selectedImage,
//                                        filePathColumn, null, null, null);
//                                cursor.moveToFirst();
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                imgDecodableString = cursor.getString(columnIndex);
//                                cursor.close();
//                                ////////////////////////////////////////////////////////////////////////
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

    @Override
    public void showMessage(Boolean result) {
        if (result){
            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("fromSignUp","signUp");
            loginIntent.putExtra("name",userDetails.getName());
            loginIntent.putExtra("email",userDetails.getEmail());
            loginIntent.putExtra("password",userDetails.getPassword());
            loginIntent.putExtra("imgUri",userDetails.getImgUri());
            startActivity(loginIntent);
        }else{
            Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadImage() {

        if(selectedImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
