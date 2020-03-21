package com.example.tripandroidproject.Model.Firebase;

import android.util.Log;

import com.example.tripandroidproject.Contract.RequestCode.RequestCodeContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRequestCodeModel {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    RequestCodeContract.IRequestCodePresenter iRequestCodePresenter;
    public FirebaseRequestCodeModel(RequestCodeContract.IRequestCodePresenter iRequestCodePresenter) {
        this.iRequestCodePresenter = iRequestCodePresenter;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("RequestCode").child(mAuth.getCurrentUser().getUid());
    }
    public void updateRequestCode(int requestCode)
    {
        myRef.child("RequestCode").setValue(requestCode);
    }
    public void getRequestCode()
    {
        String x = myRef.getKey();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                Message msgRec = dataSnapshot.getValue(Message.class);
                int requestCode = 0;
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    requestCode = Integer.parseInt(dataSnapshot1.getValue().toString());

                }
                iRequestCodePresenter.onSucess(requestCode);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("result", "Failed to read value.", error.toException());
            }
        });
//        return 5;
    }

}
