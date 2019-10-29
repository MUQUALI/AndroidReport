package com.example.firebase_demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ImageView imgInterested;
    TextView tvInterested;
    ArrayList<Place> listData;
    ArrayList<Place> gridData;

    ListView lvPlace;
    GridView gvPlace;

    ListViewAdapter lvAdapter;
    GridViewAdapter gvAdapter;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getData();

    }

    void init() {
        imgInterested = findViewById(R.id.img_interested);
        tvInterested = findViewById(R.id.tv_interested);
        listData = new ArrayList<>();
        gridData = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void getData() {

        mDatabase.child("places").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Place place = dataSnapshot.getValue(Place.class);
                listData.add(place);
                if(s != null) {
                    filterData();
                    setData();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void filterData() {
        Collections.reverse(listData);

        if(listData.size() - 2 >= 0) {
            gridData.add(listData.get(listData.size() - 2));
        }
        else {
            gridData.add(listData.get(0));
        }
        if(listData.size() - 3 >= 0) {
            gridData.add(listData.get(listData.size() - 3));
        }else {
            gridData.add(listData.get(0));
        }
    }

    void setData() {
        Place place = listData.get(listData.size() - 1);
        Picasso.get().load(place.imageUrl).into(imgInterested);
        tvInterested.setText(place.title);
    }

}


