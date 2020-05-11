package com.example.tripandroidproject.POJOs;

import android.net.Uri;

public class UserDetails {
    private String email;
    private String password;
    private String name;
    private String imgUri;
    private String firebasePhotoPath;

    public String getImgUri() {
        return imgUri;
    }


    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public UserDetails(){
    }

    public UserDetails(String email,String password){
        this.email = email;
        this.password = password;
    }

    public UserDetails(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getFirebasePhotoPath() {
        return firebasePhotoPath;
    }

    public void setFirebasePhotoPath(String firebasePhotoPath) {
        this.firebasePhotoPath = firebasePhotoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
