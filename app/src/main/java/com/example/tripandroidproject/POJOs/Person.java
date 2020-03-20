package com.example.tripandroidproject.POJOs;

public class Person {
    private String userName;
    private String email;
    private String password;
    private String photoPath;

    public Person() {
    }

    public Person(String userName, String email, String password, String photoPath) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.photoPath = photoPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
