package com.example.lostandfound;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateNewAdvertActivity extends AppCompatActivity {

    private EditText edt_name, edt_phone, edt_description, edt_date;
    private Button btn_save, btnGetCurrentLocation;
    private Geocoder geocoder;
    private FusedLocationProviderClient client;
    TextView edt_location;

    String Type;
    private DatabaseHelper databaseHelper;
    List<Address> address;
    double mLat, mLng;
    String location;

    String name,phone,date,des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_advert);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_description = findViewById(R.id.edt_description);
        edt_date = findViewById(R.id.edt_date);
        edt_location = findViewById(R.id.edt_location);
        btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation);
        client= LocationServices.getFusedLocationProviderClient(this);
        btn_save = findViewById(R.id.btn_save);
        mLat = getIntent().getDoubleExtra("mLat", 0.0);
        mLng = getIntent().getDoubleExtra("mLng", 0.0);

        name=getIntent().getStringExtra("name");
        phone=getIntent().getStringExtra("phone");
        des=getIntent().getStringExtra("description");
        date=getIntent().getStringExtra("date");


        if (mLat != 0.0) {
            geocoder = new Geocoder(CreateNewAdvertActivity.this, Locale.getDefault());
            try {
                address = geocoder.getFromLocation(mLat, mLng, 1);
                String Address = address.get(0).getAddressLine(0);
                edt_name.setText(name);
                edt_phone.setText(phone);
                edt_description.setText(des);
                edt_date.setText(date);
                edt_location.setText("" + Address);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        edt_location.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            name=edt_name.getText().toString();
            phone=edt_phone.getText().toString();
            intent.putExtra("name",name);
            intent.putExtra("phone",phone);
            intent.putExtra("description",edt_description.getText().toString());
            intent.putExtra("date",edt_date.getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        btn_save.setOnClickListener(v -> {

            String name = edt_name.getText().toString();
            String phone = edt_phone.getText().toString();
            String description = edt_description.getText().toString();
            String date = edt_date.getText().toString();
            location = edt_location.getText().toString();

            if (name.isEmpty()) {
                edt_name.setError("Name is Required");
                edt_name.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                edt_phone.setError("Phone is Required");
                edt_phone.requestFocus();
                return;
            }

            if (description.isEmpty()) {
                edt_description.setError("Description is Required");
                edt_description.requestFocus();
                return;
            }

            if (date.isEmpty()) {
                edt_date.setError("Date is Required");
                edt_date.requestFocus();
                return;
            }

            if (location.isEmpty()) {
                edt_location.setError("Location is Required");
                edt_location.requestFocus();
                return;
            }

            databaseHelper = new DatabaseHelper(getApplicationContext());
            Post post = new Post(Type, name, phone, description, date, location, String.valueOf(mLat), String.valueOf(mLng));
            boolean result = databaseHelper.createPost(post);
            if (result == true) {
                Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show();
                edt_name.setText("");
                edt_date.setText("");
                edt_description.setText("");
                edt_location.setText("");
                edt_phone.setText("");
                Intent intent = new Intent(getApplicationContext(), ShowAllAdvertActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        btnGetCurrentLocation.setOnClickListener(v -> {
            getLastLocation();
        });

    }

    public void checkButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rbtn_lost:
                if (checked)
                    Type = "Lost";
                break;
            case R.id.rbtn_found:
                if (checked)
                    Type = "Found";
                break;

        }
    }
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                Geocoder geocoder=new Geocoder(CreateNewAdvertActivity.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    mLat=addresses.get(0).getLatitude();
                                    mLng=addresses.get(0).getLongitude();
                                    String Address = addresses.get(0).getAddressLine(0);
                                    edt_location.setText("" + Address);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }



                            }

                        }
                    });


        }else
        {
            askPermission();
            Toast.makeText(this, "inValied", Toast.LENGTH_SHORT).show();

        }
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(CreateNewAdvertActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},111);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==111){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}