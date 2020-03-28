package com.example.tripandroidproject.Model.Room;

import android.content.Context;

import androidx.room.Room;

import com.example.tripandroidproject.Contract.Room.RoomPersonContract;
import com.example.tripandroidproject.POJOs.Person;
import com.example.tripandroidproject.View.UserDetails;
import java.util.List;

public class RoomPersonModel implements RoomPersonContract.IRoomPersonModel {

    private Context context;
    private AppDatabase database;
    private PersonDAO personDAO;
    private Person person;
    private List<Person> allPersons;

    public RoomPersonModel(Context context){
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "db-persons")
                .allowMainThreadQueries()
                .build();
        personDAO = database.getPersonDAO();
        person = new Person();
        allPersons = personDAO.getAllPersons();
    }

    @Override
    public void savePerson(UserDetails userDetails) {
        person.setUserName(userDetails.getName());
        person.setEmail(userDetails.getEmail());
        person.setPassword(userDetails.getPassword());
        person.setPhotoPath(userDetails.getImgUri());
        personDAO.insert(person);
    }

    @Override
    public void updatePerson(UserDetails userDetails) {

    }

    @Override
    public UserDetails getCurrentPerson(String email) {
        Person person = personDAO.getPerson(email);
        UserDetails user = new UserDetails();
        if(person!=null) {
            user.setName(person.getUserName());
            user.setEmail(person.getEmail());
            user.setPassword(person.getPassword());
            user.setImgUri(person.getPhotoPath());
        }
        return user;
    }

}