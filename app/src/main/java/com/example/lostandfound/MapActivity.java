package com.example.lostandfound;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {

    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private GoogleMap gMap;
    int Look = 0;
    private Geocoder geocoder;
    private double selectedLat, selectedLng;
    RelativeLayout bottomSheetDialog;

    List<Address> address;
    String selectedAddress;
    ProgressDialog progressDialog;
    Button btnCancel;
    Button btnAddMap;
    TextView tvMapAddress;

    SearchView searchViewMap;
    String name,phone,des,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        bottomSheetDialog = findViewById(R.id.rr_dailogBox);
        btnCancel = findViewById(R.id.btnCancel);
        tvMapAddress = findViewById(R.id.tvMapAddress);
        btnAddMap = findViewById(R.id.btnAddMap);
        searchViewMap = findViewById(R.id.searchViewMap);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        name=getIntent().getStringExtra("name");
        phone=getIntent().getStringExtra("phone");
        des=getIntent().getStringExtra("description");
        date=getIntent().getStringExtra("date");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        }
        searchViewMap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            List<Address>addressList=null;
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location=searchViewMap.getQuery().toString();
                if(location!=null)
                {
                    Geocoder geocoder1=new Geocoder(getApplicationContext());
                    try {
                        addressList=geocoder1.getFromLocationName(location,1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            gMap=googleMap;
                            Address address1=addressList.get(0);
                            LatLng latLng=new LatLng(address1.getLatitude(),address1.getLongitude());
                            MarkerOptions markerOptions=new MarkerOptions().position(latLng).title(location);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                            googleMap.addMarker(markerOptions).showInfoWindow();

                            gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    checkConnection();
                                    if (networkInfo.isConnected() && networkInfo.isAvailable()) {
                                        selectedLat = latLng.latitude;
                                        selectedLng = latLng.longitude;
                                        GetAddress(selectedLat, selectedLng);

                                    } else {
                                        Toast.makeText(MapActivity.this, "Please check your Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    });
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            gMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLatitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("you are here");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                            googleMap.addMarker(markerOptions).showInfoWindow();

                            gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    checkConnection();
                                    if (networkInfo.isConnected() && networkInfo.isAvailable()) {
                                        selectedLat = latLng.latitude;
                                        selectedLng = latLng.longitude;
                                        GetAddress(selectedLat, selectedLng);

                                    } else {
                                        Toast.makeText(MapActivity.this, "Please check your Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "premission dined", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void checkConnection() {
        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }

    private void GetAddress(double mLat, double mLng) {
        geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
        if (mLat != 0) {
            try {
                address = geocoder.getFromLocation(mLat, mLng, 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (address != null) {
                String mAddress = address.get(0).getAddressLine(0);
                String City = address.get(0).getLocality();
                selectedAddress = mAddress;

                if (mAddress != null) {

                    if (Look == 0) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(mLat, mLng);
                        markerOptions.position(latLng).title(selectedAddress);
                        gMap.addMarker(markerOptions).showInfoWindow();
                        bottomSheetDialog.setVisibility(View.VISIBLE);
                        tvMapAddress.setText(selectedAddress);
                        createBottomSheetLoad(mLat, mLng);
                        Look = 1;
                    }
                    else {
                        Toast.makeText(this, "please Cancel or Add press", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Some thing wrong", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "LatLng null", Toast.LENGTH_SHORT).show();
        }
    }

    private void createBottomSheetLoad(double mLat, double mLng) {

        btnCancel.setOnClickListener(view1 -> {
            bottomSheetDialog.setVisibility(View.GONE);

            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone);
            intent.putExtra("description",des);
            intent.putExtra("date",date);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Look = 0;
        });
        btnAddMap.setOnClickListener(v -> {
            bottomSheetDialog.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), CreateNewAdvertActivity.class);
            intent.putExtra("mLat", mLat);
            intent.putExtra("mLng", mLng);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone);
            intent.putExtra("description",des);
            intent.putExtra("date",date);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}