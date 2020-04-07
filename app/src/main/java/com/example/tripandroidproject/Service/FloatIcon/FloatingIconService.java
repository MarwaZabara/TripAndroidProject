package com.example.tripandroidproject.Service.FloatIcon;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.example.tripandroidproject.Presenter.Trip.FinishTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.FloatIcon.FloatAdapter;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NavDrawer;
import com.example.tripandroidproject.View.UnderTest.TestReminder;

import java.util.List;

public class FloatingIconService extends Service {
    private WindowManager mWindowManager;
    private View mFloatingView;
    RecyclerView recyclerView;
    private String tripID;
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        return START_NOT_STICKY;
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int LAYOUT_FLAG;
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_icon, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
//Add the view to the window.
        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
//The root element of the collapsed view layout
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = mFloatingView.findViewById(R.id.expanded_container);



        recyclerView = mFloatingView.findViewById(R.id.noteFloatRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tripID = intent.getStringExtra("tripID");
        List<Note> notes = getNotes(tripID);
        GetOfflineTripPresenter getOfflineTripPresenter = new GetOfflineTripPresenter(this);
        Trip trip =  getOfflineTripPresenter.getTripInfoWithId(tripID);
        FloatAdapter floatAdapter = new FloatAdapter(this,notes,trip.getRepeatEvery());
        recyclerView.setAdapter(floatAdapter);



        controls();
        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
//                            if (isViewCollapsed()) {
//                            if(FloatingIconService.this.intent == null){
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
//                                startActivity(intent);
//                                s
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);

//                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });

        return START_NOT_STICKY;
    }

    private List<Note> getNotes(String tripID) {

        GetNotePresenter getNotePresenter = new GetNotePresenter(this);
        List<Note> notes= getNotePresenter.getNotes(tripID);
        return notes;
    }

    private void Move(final WindowManager.LayoutParams params) {

    }
    private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }
    private void controls() {
        //The root element of the collapsed view layout
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = mFloatingView.findViewById(R.id.expanded_container);


//        //Set the close button
        ImageView closeButtonCollapsed = (ImageView) mFloatingView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the service and remove the from from the window
                stopSelf();
            }
        });
//        Set the close button
        Button closeButton = mFloatingView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
        Button finishBtn = mFloatingView.findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishTripPresenter finishTripPresenter = new FinishTripPresenter(FloatingIconService.this);
                finishTripPresenter.finishTrip(tripID);
            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}
