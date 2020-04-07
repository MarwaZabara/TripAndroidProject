package com.example.tripandroidproject.Model.Room;

import android.content.Context;

import androidx.room.Room;

import com.example.tripandroidproject.Contract.Trip.DeleteOfflineTripContract;
import com.example.tripandroidproject.Contract.Trip.GetOfflineTripContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.Contract.Trip.UpdateTripOfflineContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseUserModel;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.DeleteOfflineTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.UpdateTripOfflinePresenter;

import java.util.List;

public class RoomTripModel implements SaveTripContract.ISaveTripOfflineModel , GetOfflineTripContract.IGetOfflineTripModel , DeleteOfflineTripContract.IDeleteOfflineTripModel, UpdateTripOfflineContract.IUpdateTripOfflineModel {
    private final AppDatabase database;
    private final TripDAO tripDAO;
    private ITripPresenter tripPresenter;
    private DeleteOfflineTripPresenter offlineTripPresenter;
    private UpdateTripOfflinePresenter updateTripOfflinePresenter;

    public RoomTripModel(Context context) {    //////// this constructor for delete offline
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .fallbackToDestructiveMigration()
                .build();
        tripDAO = database.getTripDAO();
    }

//    public RoomTripModel(Context context, UpdateTripOfflinePresenter presenter) {    //////// this constructor for update offline
//        this.updateTripOfflinePresenter =  presenter;
//        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
//                .allowMainThreadQueries()   //Allows room to do operation on main thread
//                .build();
//        tripDAO = database.getTripDAO();
//    }

    public RoomTripModel(ITripPresenter tripPresenter, Context context) {
        this.tripPresenter = tripPresenter;
        database = Room.databaseBuilder(context, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .fallbackToDestructiveMigration()
                .build();
        tripDAO = database.getTripDAO();
    }

    public void saveTrip(Trip trip) {
        tripDAO.insert(trip);
    }

    @Override
    public List<Trip> getOfflineTrip() {
        return tripDAO.getOfflineTrips();
    }
    @Override
    public List<Trip> getAllOfflineTrip() {
        return tripDAO.getAllOfflineTrips();
    }

    @Override
    public List<Trip> getOfflineFilteredTrip(String filter) {
        return tripDAO.getOfflineFilteredTrips(filter);
    }

    @Override
    public List<Trip> getOfflineFilteredTrip(String filter1,String filter2) {
        return tripDAO.getOfflineFilteredTrips(filter1, filter2);
    }

    @Override
    public Trip getTripForSpecificCode(int requsetCode) {
        return tripDAO.getTripForSpecificCode(requsetCode);
    }
    @Override
    public List<Trip> getTrips() {
        return tripDAO.getTrips(FirebaseUserModel.getUserID());
    }

    public Trip startTrip(String tripID) {
        return tripDAO.getTripForID(tripID);
    }

    @Override
    public void updateTrip(Trip trip) {
        tripDAO.update(trip);
    }

    public Trip getTripForSpecificID(String tripID) {
        return tripDAO.getTripForID(tripID);
    }
    @Override
    public void deleteOfflineTrip(Trip trip) {
        tripDAO.delete(trip);
    }

    public Trip getTripInfoWithId(String tripID) {
        return tripDAO.getTripForID(tripID);
    }
}
