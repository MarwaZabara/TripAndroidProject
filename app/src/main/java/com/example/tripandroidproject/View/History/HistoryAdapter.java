package com.example.tripandroidproject.View.History;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.ContextMenu;
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

import com.example.tripandroidproject.Contract.Trip.DeleteTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Reminder.StartTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.DeleteTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.TripAdapter;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final Context context;
    private List<Trip> values;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private String tripName;
    private View view;
    private DeleteTripContract.IDeleteTripPresenter presenter;
    private HistoryAdapter.CancelledTrip cancelledTrip;

    public HistoryAdapter(@NonNull Context context, @NonNull List<Trip> myDataSet, HistoryAdapter.CancelledTrip cancelledTrip) {
        values = myDataSet;
        this.context = context;
        this.cancelledTrip = cancelledTrip;
        presenter = new DeleteTripPresenter();
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recycleView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recycleView.getContext());
        view = inflater.inflate(R.layout.trip_row, recycleView, false);
        ViewHolder vh = new ViewHolder(view, cancelledTrip);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.ViewHolder holder, final int position) {
        holder.date.setText(values.get(position).getDate());
        holder.time.setText(values.get(position).getTime());
        holder.status.setText(values.get(position).getStatus());
        holder.location1.setText(values.get(position).getDescription());
        holder.location2.setText(values.get(position).getDescription());
        holder.tripName.setText(values.get(position).getName());
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Toast.makeText(context, values.get(position).getName(), Toast.LENGTH_SHORT).show();
                tripName = values.get(position).getName();

                takeAction(tripName, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView date, time, location1, location2, status, tripName;
        public Button startTrip;
        public ConstraintLayout constraintLayout;
        HistoryAdapter.CancelledTrip cancelledTrip;
        public View layout;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView, HistoryAdapter.CancelledTrip cancelledTrip) {
            super(itemView);
            this.cancelledTrip = cancelledTrip;
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

            itemView.setOnClickListener(this);

            startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartTripPresenter startTripPresenter = new StartTripPresenter(context);
                    String destination = "1+محمود+سلامة،+كوم+الدكة+غرب،+العطارين،+الإسكندرية";
                    startTripPresenter.startTrip(destination, "trip1", 1);
                }
            });
        }

        @Override
        public void onClick(View v) {
            cancelledTrip.getPosition(getAdapterPosition());
        }
    }

    private void takeAction(final String tripName, final int position) {
        final CharSequence[] options = {"Delete Trip", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Take Action on " + tripName);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Delete Trip")) {
                    final CharSequence[] alert = {};
                    confirmation(position);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void confirmation(final int position) {
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
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void removeItem(int position) {
        presenter.deleteTrip(values.get(position));
        values.remove(position);
        notifyItemRemoved(position);
    }

    public interface CancelledTrip {
        void getPosition(int position);
    }
}

