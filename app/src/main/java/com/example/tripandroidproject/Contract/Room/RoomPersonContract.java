package com.example.tripandroidproject.Contract.Room;

import com.example.tripandroidproject.POJOs.Person;
import com.example.tripandroidproject.View.UserDetails;

public class RoomPersonContract {
    public interface IRoomPersonView{
        UserDetails setCurrentPerson(UserDetails userDetails);
    }

    public interface IRoomPersonPresenter{
        void setCurrentPerson(UserDetails userDetails);
    }

    public interface IRoomPersonModel{
        void savePerson(UserDetails userDetails);
        void updatePerson(UserDetails userDetails);
        UserDetails getCurrentPerson(String email);
    }
}
