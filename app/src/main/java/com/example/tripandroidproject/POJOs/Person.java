package com.example.tripandroidproject.POJOs;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {
    private String name;
    @PrimaryKey
    @NonNull
    private String email;
    private String password;
    private String imgUri;
    private String firebasePhotoPath;


    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getFirebasePhotoPath() {
        return firebasePhotoPath;
    }

    public void setFirebasePhotoPath(String firebasePhotoPath) {
        this.firebasePhotoPath = firebasePhotoPath;
    }
}
