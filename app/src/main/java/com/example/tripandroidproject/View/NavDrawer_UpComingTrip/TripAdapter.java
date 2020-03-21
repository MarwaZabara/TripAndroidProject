package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.Service.FloatIcon.FloatingIconService;
import com.example.tripandroidproject.View.UnderTest.TestReminder;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder>  {

    private final Context context;
    private List<Trip> values;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    public TripAdapter(@NonNull Context context, @NonNull List<Trip> myDataSet) {
        values = myDataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recycleView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recycleView.getContext());
        View v = inflater.inflate(R.layout.trip_row,recycleView,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ViewHolder holder, final int position) {
        holder.date.setText(values.get(position).getDate());
        holder.time.setText(values.get(position).getTime());
        holder.status.setText(values.get(position).getStatus());
        holder.location1.setText(values.get(position).getDescription());
        holder.location2.setText(values.get(position).getDescription());
        holder.tripName.setText(values.get(position).getName());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, values.get(position).getName(), Toast.LENGTH_SHORT).show();
                ////////////////////////////if need do some thing when click///////////////////////
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date,time,location1,location2,status,tripName;
        public Button startTrip;
        public ConstraintLayout constraintLayout;
        public View layout;
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

            startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartTrip();
                }
            });
        }
        private void StartTrip() {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=1+محمود+سلامة،+كوم+الدكة+غرب،+العطارين،+الإسكندرية"));
            context.startActivity(intent);
            startFloatIcon();
        }

        private void startFloatIcon() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                //If the draw over permission is not available open the settings screen
                //to grant the permission.
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
//                startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
            } else {
                initializeView();
            }
        }
        private void initializeView() {
            context.startService(new Intent(context, FloatingIconService.class));
        }

    }
}
