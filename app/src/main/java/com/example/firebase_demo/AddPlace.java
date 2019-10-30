package com.example.firebase_demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPlace extends AppCompatActivity {
    ImageView imgPlace;
    EditText edtPlaceName, edtTitle, edtAddress, edtBus, edtHotel, edtMorePlace, edtFood;
    Date dtPostDate;
    MultiAutoCompleteTextView edtDetail;
    Button btnPost;

    DatabaseReference mDatabase;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://androidbtl-b3b5d.appspot.com");
    StorageReference storageRef;

    String placeName, title, address, postDate, bus, hotel, morePlace, food, detail;
    private static final int PICK_IMAGE = 1;
    private String IMAGE_URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        init();
        eventsHandler();
    }

    void init() {
        // firebase init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = storage.getReference();

        // views init
        imgPlace = findViewById(R.id.img_place);
        edtPlaceName = findViewById(R.id.edt_place_name);
        edtTitle = findViewById(R.id.edt_title);
        edtAddress = findViewById(R.id.edt_address);
        edtBus = findViewById(R.id.edt_bus);
        edtHotel = findViewById(R.id.edt_hotel);
        edtMorePlace = findViewById(R.id.edt_more_place);
        edtFood = findViewById(R.id.edt_food);
        edtDetail = findViewById(R.id.area_detail);

        btnPost = findViewById(R.id.btn_post);
    }

    void eventsHandler() {

        imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postEvent();
            }
        });
    }

    void postEvent() {
        placeName = edtPlaceName.getText().toString();
        title = edtTitle.getText().toString();

        Calendar calendar = Calendar.getInstance();
        dtPostDate = calendar.getTime();

        address = edtAddress.getText().toString();
        hotel = edtHotel.getText().toString();
        bus = edtBus.getText().toString();
        food = edtFood.getText().toString();
        morePlace = edtMorePlace.getText().toString();
        detail = edtDetail.getText().toString();

        Place place = new Place(placeName, title, dtPostDate, address, bus, hotel, morePlace, food, detail, IMAGE_URL);

        mDatabase.child("places").push().setValue(place, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError == null) {
                    Toast.makeText(AddPlace.this, "Đăng thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddPlace.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //data.getData();
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imgPlace.setImageURI(imageUri);
            uploadHandler();
        }

    }

    void uploadHandler() {
        Calendar time = Calendar.getInstance();
        final String path = "images/place" + time.getTimeInMillis() + ".png";
        final StorageReference mountainsRef = storageRef.child(path);

        // Get the data from an ImageView as bytes
        imgPlace.setDrawingCacheEnabled(true);
        imgPlace.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgPlace.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddPlace.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AddPlace.this, "Thành Công", Toast.LENGTH_SHORT).show();
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return mountainsRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    IMAGE_URL = downloadUri.toString();
                    Log.d("URL: ", "onSuccess: " + downloadUri);
                } else {
                    Toast.makeText(AddPlace.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
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
            case R.id.b_home:
                Intent intent = new Intent(AddPlace.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
