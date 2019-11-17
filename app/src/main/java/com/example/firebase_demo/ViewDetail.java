package com.example.firebase_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewDetail extends AppCompatActivity {
    TextView tvTitle,tvAddress, tvDate, tvBus, tvHotel, tvMorePlace, tvFood, tvDetail, tvPhone;
    ImageView imgDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        init();
        prepareData();
        callAction();
    }

    void init() {
        tvTitle = findViewById(R.id.tv_title_detail);
        tvAddress = findViewById(R.id.tv_address_detail);
        tvDate = findViewById(R.id.tv_date_detail);
        tvBus = findViewById(R.id.tv_bus_detail);
        tvHotel = findViewById(R.id.tv_hotel_detail);
        tvMorePlace = findViewById(R.id.tv_more_detail);
        tvFood = findViewById(R.id.tv_food_detail);
        tvDetail = findViewById(R.id.tv_des_detail);
        tvPhone = findViewById(R.id.tv_phone_detail);
        imgDetail = findViewById(R.id.img_view_detail);

    }

    void prepareData() {
        Intent intent = getIntent();
        Place place = (Place) intent.getSerializableExtra("data");

        Picasso.get().load(place.imageUrl).into(imgDetail);
        tvTitle.setText(place.title);
        tvAddress.setText(place.address);
        tvDate.setText(place.postDate.toString());
        tvBus.setText(place.bus);
        tvHotel.setText(place.hotel);
        tvMorePlace.setText(place.morePlace);
        tvFood.setText(place.food);
        tvDetail.setText(place.detail);
        tvPhone.setText(place.phone);
    }

    void callAction() {
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + tvPhone.getText().toString()));
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
                Intent intent = new Intent(ViewDetail.this, AddPlace.class);
                startActivity(intent);
                break;
            case R.id.b_home:
                intent = new Intent(ViewDetail.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
