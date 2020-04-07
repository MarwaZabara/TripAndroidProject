package com.example.tripandroidproject.View.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Firebase.FirebaseUserContract;
import com.example.tripandroidproject.Custom.Toast.CustomToast;
import com.example.tripandroidproject.InternetConnection.CheckInternetConnection;
import com.example.tripandroidproject.Model.Room.RoomPersonModel;
import com.example.tripandroidproject.Presenter.SignUp.SignUpPresenter;
import com.example.tripandroidproject.Presenter.User.FirebaseUserPresenter;
import com.example.tripandroidproject.Presenter.User.UserPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.POJOs.Person;
import com.example.tripandroidproject.View.SignUp.SignupActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment implements FirebaseUserContract.IUserView {

    private EditText name;
    private TextView email;
    private ImageView imageView;
    private Button save,edit,editImg;
    private FirebaseUserPresenter firebaseUserPresenter;
    private SaveUserLogIn getUserLogIn;
    private Person user;
    private CheckInternetConnection checkInternetConnection;
    private Uri selectedImage;
    private SignUpPresenter presenter;
    private StorageReference mStorageRef;
    FirebaseStorage storage;
    StorageReference storageReference;
    private String usrImgUri = "";
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = view.findViewById(R.id.proName);
        email = view.findViewById(R.id.proEmail);
        imageView = view.findViewById(R.id.proImg);
        save = view.findViewById(R.id.proSave);
        edit = view.findViewById(R.id.proEdit);
        editImg = view.findViewById(R.id.editImg);
        context = this.getContext();
//        presenter = new SignUpPresenter(this.getContext());
        beforeEdit();
        checkInternetConnection = new CheckInternetConnection();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseUserPresenter = new FirebaseUserPresenter();
        getUserLogIn = new SaveUserLogIn(this.getContext());


        ////////////////////////////// to ignore edit btn
        edit.setVisibility(View.GONE);




        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusableInTouchMode(true);
                editImg.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person user = new Person();
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                if(user.getFirebasePhotoPath() != null) {
                    if (user.getPassword() != null) {
                        mStorageRef = FirebaseStorage.getInstance().getReference();
                        Uri uri = Uri.parse(user.getFirebasePhotoPath());
                        StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/" + user.getFirebasePhotoPath());
                        final long ONE_MEGABYTE = 1024 * 1024;
                        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytesPrm) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.length);
                                imageView.setImageBitmap(bmp);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                imageView.setImageResource(R.mipmap.ic_launcher);
                            }
                        });
                    } else {
                        Picasso.get().load(user.getFirebasePhotoPath()).resize(120, 120).centerCrop().into(imageView);
                    }
                }
//                setImageInView();
                UserPresenter userPresenter = new UserPresenter(context);
                userPresenter.updateUserInRoom(user);
                firebaseUserPresenter.updateUser(user);
                edit.setVisibility(View.VISIBLE);
                beforeEdit();
            }
        });
        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               chooseImage();
//                selectImage();

            }
        });

        RoomPersonModel roomPersonModel = new RoomPersonModel(getContext());
        user = roomPersonModel.getUser();
        name.setText(user.getName());
        email.setText(user.getEmail());
        if(user.getFirebasePhotoPath() != null) {
            if (user.getPassword() != null) {
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/" + user.getFirebasePhotoPath());
                final long ONE_MEGABYTE = 1024 * 1024;
                ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytesPrm) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.length);
                        imageView.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        imageView.setImageResource(R.mipmap.ic_launcher);
                    }
                });
            } else {
                Picasso.get().load(user.getFirebasePhotoPath()).resize(120, 120).centerCrop().into(imageView);
            }
    }
        return view;
    }

    @Override
    public void setUserData(Person user) {
            this.user = user;
    }

    void beforeEdit(){
        name.setFocusable(false);
        editImg.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
    }


//     private void selectImage() {
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        builder.setTitle("Choose your profile picture");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    pickPhoto.setType("image/*");
//                    startActivityForResult(pickPhoto , 1);
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    public void setImageInView() {
//        if (checkInternetConnection.getConnectivityStatusString(this.getContext())) {
//
//                if(selectedImage != null) {
//                    String imageUrl = UUID.randomUUID().toString();
//                    user.setFirebasePhotoPath(imageUrl);
//                    UserPresenter userPresenter = new UserPresenter(this.getContext());
//                    userPresenter.updateUserInRoom(user);
//                }
//                user.setImgUri(usrImgUri);
//                presenter.onSendData(user);
//                uploadImage(user.getFirebasePhotoPath());
//
//            }
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 0:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Bitmap selectedImage1 = (Bitmap) data.getExtras().get("data");
//                        imageView.setImageBitmap(selectedImage1);
//                    }
//                    break;
//                case 1:
//                    if (resultCode == RESULT_OK && data != null && data.getData()!=null) {
//                        selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImage != null) {
//                            usrImgUri = selectedImage.toString();
//                            try {
//                                usrImgUri = selectedImage.toString();
//                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImage);
//                                imageView.setImageBitmap(bitmap.createScaledBitmap(bitmap, 120, 120, false));
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    break;
//            }
//        }
//    }
//
//    private void uploadImage(String url) {
//
//        if(selectedImage != null)
//        {
//            final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            StorageReference ref = storageReference.child("images/"+ url);
//            ref.putFile(selectedImage)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                        }
//                    });
//        }
//    }

}
