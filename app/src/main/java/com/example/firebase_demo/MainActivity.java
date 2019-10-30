package com.example.firebase_demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
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
        tranfersActivity();
    }

    void init() {
        imgInterested = findViewById(R.id.img_interested);
        tvInterested = findViewById(R.id.tv_interested);
        listData = new ArrayList<>();
        gridData = new ArrayList<>();

        lvPlace = findViewById(R.id.lv_place);
        gvPlace = findViewById(R.id.gv_place);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void getData() {

        mDatabase.child("places").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Place place = dataSnapshot.getValue(Place.class);
                listData.add(place);
                gridData.add(place);
                if(s != null) {
                    filterData();
                    setData();
                    configView();
                }
                mainImageClick();

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
    }

    void setData() {
        Place place = listData.get(listData.size() - 1);
        Picasso.get().load(place.imageUrl).into(imgInterested);
        tvInterested.setText(place.title);
    }

    void configView() {
        lvAdapter = new ListViewAdapter(listData, MainActivity.this, R.layout.place_item_layout);
        lvPlace.setAdapter(lvAdapter);

        gvAdapter = new GridViewAdapter(gridData, MainActivity.this, R.layout.place_grid_layout);
        gvPlace.setAdapter(gvAdapter);
    }

    void tranfersActivity() {
        listViewClick();
        gridViewClick();
    }

    void listViewClick() {
        lvPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewDetail.class);
                Place place = listData.get(i);
                intent.putExtra("data", place);
                startActivity(intent);
            }
        });
    }

    void gridViewClick() {
        gvPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewDetail.class);
                Place place = gridData.get(i);
                intent.putExtra("data", place);
                startActivity(intent);
            }
        });
    }

    void mainImageClick() {
        imgInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDetail.class);
                Place place = listData.get(listData.size() - 1);
                intent.putExtra("data", place);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_place:
                Intent intent = new Intent(MainActivity.this, AddPlace.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


