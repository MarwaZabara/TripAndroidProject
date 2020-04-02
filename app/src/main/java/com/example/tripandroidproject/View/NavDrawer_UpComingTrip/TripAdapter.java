package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Trip.UpdateTripContract;
import com.example.tripandroidproject.Contract.Trip.UpdateTripOfflineContract;
import com.example.tripandroidproject.InternetConnection.CheckInternetConnection;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.example.tripandroidproject.Presenter.Trip.CancelTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.DeleteTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.FinishTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.StartTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.Service.FloatIcon.FloatingIconService;
import com.example.tripandroidproject.View.AddTrip.AddTripActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder>  {

    private final Context context;
    private List<Trip> upComingTripList;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private String tripName;
    private String status;
    private String TID;
    private View view;
    private DeleteTripPresenter deleteTripPresenter;
    private CheckInternetConnection checkInternetConnection;
    private UpdateTripContract.IUpdateTripPresenter cancelTripPresenter;
    private UpdateTripOfflineContract.IUpdateTripOfflinePresenter updateTripOfflinePresenter;
    String location2;
    public TripAdapter(@NonNull Context context, @NonNull List<Trip> myDataSet) {
        upComingTripList = myDataSet;
        this.context = context;
        deleteTripPresenter = new DeleteTripPresenter(context);
        cancelTripPresenter = new CancelTripPresenter(context);
        checkInternetConnection = new CheckInternetConnection();
    }

    @NonNull
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recycleView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recycleView.getContext());
        view = inflater.inflate(R.layout.trip_row,recycleView,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final TripAdapter.ViewHolder holder, final int position) {
        ////////////////////to get start/end Locations///////////////////////////////////////////
        double lat1 = upComingTripList.get(position).getStartLatitude();
        double long1 = upComingTripList.get(position).getStartLongitude();
        double lat2 = upComingTripList.get(position).getEndLatitude();
        double long2 = upComingTripList.get(position).getEndLongitude();
        String location1 = getRegionName(lat1,long1);
        location2 = getRegionName(lat2,long2);
        holder.date.setText(upComingTripList.get(position).getDate());
        holder.time.setText(upComingTripList.get(position).getTime());
        holder.status.setText(upComingTripList.get(position).getStatus());
        holder.location1.setText(location1);
        holder.location2.setText(location2);
        holder.tripName.setText(upComingTripList.get(position).getName());
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tripName = upComingTripList.get(position).getName();
                status =  upComingTripList.get(position).getStatus();
                TID = upComingTripList.get(position).getId();
                takeAction(tripName,status,position,TID);
                return true;
            }
        });
//        holder.constraintLayout.setOnHoverListener();
//        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////        Toast.makeText(context, values.get(position).getName(), Toast.LENGTH_SHORT).show();
////                tripName = upComingTripList.get(position).getName();
////                status =  upComingTripList.get(position).getStatus();
////                TID = upComingTripList.get(position).getId();
////                takeAction(tripName,status,position,TID);
//            }
//        });
        holder.startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upComingTripList.get(position).getStatus().equals("upcoming") || upComingTripList.get(position).getStatus().equals("repeated")){
                    StartTripPresenter startTripPresenter = new StartTripPresenter(context);
                    String destination = location2;
                    startTripPresenter.startTrip(destination, upComingTripList.get(position).getId(), upComingTripList.get(position).getRequestCodeHome());
                }
                else if (upComingTripList.get(position).getStatus().equals("finished") || upComingTripList.get(position).getStatus().equals("Cancel")){
                    Toast.makeText(context, "in notes", Toast.LENGTH_SHORT).show();
                    showDialogListView(upComingTripList.get(position).getId());
                }

            }
        });
        if(upComingTripList.get(position).getStatus().equals("finished") || upComingTripList.get(position).getStatus().equals("Cancel") )
        {
//            holder.startTrip.setVisibility(View.INVISIBLE);
            holder.startTrip.setText("Show Notes");
        }
        else if(upComingTripList.get(position).getStatus().equals("start") || upComingTripList.get(position).getStatus().equals("repeated_Start"))
        {
            holder.startTrip.setText("Show Details");
        }
    }

    @Override
    public int getItemCount() {
        return upComingTripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView date,time,location1,location2,status,tripName;
        public Button startTrip;
        public ConstraintLayout constraintLayout;
        public View layout;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            location1 = itemView.findViewById(R.id.location1);
            location2 = itemView.findViewById(R.id.location2);
            status = itemView.findViewById(R.id.status);
            tripName = itemView.findViewById(R.id.tripName);
            startTrip = itemView.findViewById(R.id.startTripId);
            constraintLayout = itemView.findViewById(R.id.row);
            cardView = itemView.findViewById(R.id.myCardView);

        }
    }

    private void takeAction(final String tripName, final String status, final int position, final String TID) {
        final CharSequence[] options;
        if(status.equalsIgnoreCase("upcoming")||status.equalsIgnoreCase("repeated")) {
            options = new CharSequence[]{"Edit Trip", "Delete Trip", "Cancel Trip"};
        }
        else if(status.equalsIgnoreCase("start")){
            options = new CharSequence[]{"Finish Trip"};
        }
        else {
             options = new CharSequence[]{"Delete Trip"};
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Take Action on " + tripName);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Edit Trip")) {
                    Intent editIntent = new Intent(context, AddTripActivity.class);
                    editIntent.putExtra("isEdit",true);
                    editIntent.putExtra("trip",upComingTripList.get(position));
                    context.startActivity(editIntent);

                } else if (options[item].equals("Delete Trip")) {
//                    Trip trip = upComingTripList.get(position);
//                    trip.setStatus("delete");     ///////change status of trip
//                    updateTripOfflinePresenter.updateTrip(trip);
//                    notifyItemRemoved(position);
                    confirmation(position);

                } else if (options[item].equals("Cancel Trip")) {
                    Trip trip = upComingTripList.get(position);
//                    trip.setStatus("Cancel");     ///////change status of trip
                    cancelTripPresenter.cancelTrip(trip);
//                    updateTripOfflinePresenter.updateTrip(trip);
                    upComingTripList.remove(position);
                    notifyItemRemoved(position);
//                  communicatorFrag.cancelTrip(trip);
//                    removeItem(position);               //// function to remove trip from arrayInRecycleView and room
                }
                else if (options[item].equals("Finish Trip")) {
                    Trip trip = upComingTripList.get(position);
//                    trip.setStatus("Cancel");     ///////change status of trip
                    FinishTripPresenter finishTripPresenter = new FinishTripPresenter(context);
                    finishTripPresenter.finishTrip(trip.getId());
//                    updateTripOfflinePresenter.updateTrip(trip);
                    upComingTripList.remove(position);
                    notifyItemRemoved(position);
//                  communicatorFrag.cancelTrip(trip);
//                    removeItem(position);               //// function to remove trip from arrayInRecycleView and room
                }
//                else if (options[item].equals("Notes")) {
//                    Toast.makeText(context, "in notes", Toast.LENGTH_SHORT).show();
//                    showDialogListView(TID);
//                }
            }
        });
        builder.show();
    }

    public void showDialogListView(String TID) {
        Dialog dialog = new Dialog(context);
        List<Note> notes = getNotes(TID);
        dialog.setContentView(R.layout.notes_recycler_view);
        dialog.setCancelable(true);
        RecyclerView recyclerView = dialog.findViewById(R.id.NotesRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter = new NotesAdapter(context,notes);
        recyclerView.setAdapter(adapter);

        if (this.context.getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            // Toast.makeText(getApplicationContext(),"in startAutoComplete",Toast.LENGTH_LONG).show();
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = 100 ;
            recyclerView.setLayoutParams(params);
            recyclerView.requestLayout();
        }
        else if (this.context.getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            // Toast.makeText(getApplicationContext(),"portrait",Toast.LENGTH_LONG).show();
            params.height = 1000;
            recyclerView.setLayoutParams(params);
            recyclerView.requestLayout();
        }
        adapter.notifyDataSetChanged();
        dialog.show();
    }

    private List<Note> getNotes(String tripID) {
        GetNotePresenter getNotePresenter = new GetNotePresenter(context);
        List<Note> notes= getNotePresenter.getNotes(tripID);
        return notes;
    }


    private void confirmation(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.alert_delete_trip,
                (ConstraintLayout) view.findViewById(R.id.deleteAlertDialog));
        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();
        view1.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);    //// function to remove trip from arrayInRecycleView and room
                alertDialog.dismiss();
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void removeItem(int position) {
        Trip trip = upComingTripList.get(position);
        deleteTripPresenter.deleteTrip(trip);
//        if (checkInternetConnection.getConnectivityStatusString(context)) {
//            presenter.deleteTrip(trip);    ///////////delete from firebase
//            deleteOfflineTripPresenter.deleteOfflineTrip(trip); /////delete from room
//            upComingTripList.remove(position);   /////remove trip from arrayInRecycleView
//        } else if (!checkInternetConnection.getConnectivityStatusString(context) && trip.getIsSync()==1){
//            //////// isSync = 1 mean it stored in firebase allready and need to delete it from firebase & room
//            trip.setIsSync(0);
//            trip.setStatus("delete");
//            updateTripPresenter.updateTrip(trip);
//            /////// -> here send trip to Hassan
//            upComingTripList.remove(position);   /////remove trip from arrayInRecycleView
//
//        } else if (!checkInternetConnection.getConnectivityStatusString(context) && trip.getIsSync()==0){
//            //////// isSync = 0 mean it didn't store in firebase so need to delete it from room only
////            trip.setIsSync(1);
////            updateTripPresenter.updateTrip(trip);
//            deleteOfflineTripPresenter.deleteOfflineTrip(trip); /////delete from room
//            /////// -> here send trip to Hassan
//            upComingTripList.remove(position);   /////remove trip from arrayInRecycleView
//        }
        upComingTripList.remove(position);   /////remove trip from arrayInRecycleView
        notifyItemRemoved(position);
    }

    private String getRegionName(Double lat,Double lon) {
        String region = "";
        List<Address> addresses;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            if (lat>0 & lon>0) {
                addresses = geocoder.getFromLocation(lat, lon, 1);
                String address = addresses.get(0).getAddressLine(0);
                region = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }
}
