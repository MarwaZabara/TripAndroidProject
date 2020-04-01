package com.example.tripandroidproject.Contract.Room;

import com.example.tripandroidproject.POJOs.Person;

public class RoomPersonContract {
    public interface IRoomPersonView{
        Person setCurrentPerson(Person userDetails);


    }

    public interface IRoomPersonPresenter{
        void setCurrentPerson(Person userDetails);
    }

    public interface IRoomPersonModel{
        void savePerson(Person userDetails);
        void updatePerson(Person userDetails);
        Person getCurrentPerson(String email);
    }
}
