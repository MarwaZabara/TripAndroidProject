package com.example.tripandroidproject.View.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tripandroidproject.Contract.Firebase.FirebaseUserContract;
import com.example.tripandroidproject.Model.Room.RoomPersonModel;
import com.example.tripandroidproject.Presenter.User.FirebaseUserPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.POJOs.Person;

public class Profile extends Fragment implements FirebaseUserContract.IUserView {

    private EditText name,email;
    private ImageView imageView;
    private Button save,edit,editImg;
    private FirebaseUserPresenter firebaseUserPresenter;
    private SaveUserLogIn getUserLogIn;
    private Person user;

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

        beforeEdit();

        firebaseUserPresenter = new FirebaseUserPresenter();
        getUserLogIn = new SaveUserLogIn(this.getContext());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                editImg.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person user = new Person();
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setImgUri(" ");
//                getUserLogIn.storeUserData(user);
                firebaseUserPresenter.updateUser(user);
                beforeEdit();
            }
        });
        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////choose Img
            }
        });

//        user = new Person();
//        if (getUserLogIn.getLoggedInUser()!=null)
//        {
//            user = getUserLogIn.getLoggedInUser();
//        }else {
//            firebaseUserPresenter.getUserData();
//        }
        RoomPersonModel roomPersonModel = new RoomPersonModel(getContext());
        user = roomPersonModel.getUser();
        name.setText(user.getName());
        email.setText(user.getEmail());
        return view;
    }

    @Override
    public void setUserData(Person user) {
            this.user = user;
    }

    void beforeEdit(){
        name.setFocusable(false);
        email.setFocusable(false);
        editImg.setVisibility(View.GONE);
    }

}
