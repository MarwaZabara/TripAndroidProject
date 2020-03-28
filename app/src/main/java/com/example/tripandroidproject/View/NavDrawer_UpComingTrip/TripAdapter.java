package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.StartTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.DeleteTripPresenter;
import com.example.tripandroidproject.R;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder>  {

    private final Context context;
    private List<Trip> values;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private String tripName;
    private View view;
    private DeleteTripPresenter presenter;

    public TripAdapter(@NonNull Context context, @NonNull List<Trip> myDataSet) {
        values = myDataSet;
        this.context = context;
        presenter = new DeleteTripPresenter();
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
        holder.date.setText(values.get(position).getDate());
        holder.time.setText(values.get(position).getTime());
        holder.status.setText(values.get(position).getStatus());
        holder.location1.setText(values.get(position).getDescription());
        holder.location2.setText(values.get(position).getDescription());
        holder.tripName.setText(values.get(position).getName());
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Toast.makeText(context, values.get(position).getName(), Toast.LENGTH_SHORT).show();
        tripName = values.get(position).getName();

        takeAction(tripName,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
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

            startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartTripPresenter startTripPresenter = new StartTripPresenter(context);
                    String destination = "1+محمود+سلامة،+كوم+الدكة+غرب،+العطارين،+الإسكندرية";
                    startTripPresenter.startTrip(destination,"trip1",1);
                }
            });
        }
    }

    private void takeAction(final String tripName, final int position) {
        final CharSequence[] options = { "Edit Trip", "Delete Trip","Cancel Trip" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Take Action on " + tripName);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Edit Trip")) {



                } else if (options[item].equals("Delete Trip")) {
                    final CharSequence[] alert = {};
                    confirmation(position);
                    notifyDataSetChanged();

                } else if (options[item].equals("Cancel Trip")) {
                    Trip trip = values.get(position);
                    trip.setStatus("Cancel");
                    notifyDataSetChanged();
//                    communicatorFrag.cancelTrip(trip);
                    values.remove(position);
                }
            }
        });
        builder.show();
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
                removeItem(position);
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
        presenter.deleteTrip(values.get(position));
        values.remove(position);
        notifyItemRemoved(position);
    }
}
