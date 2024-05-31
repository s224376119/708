package com.example.lostandfound;

import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ShowAllMap extends AppCompatActivity {


    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private GoogleMap gMap;
    private Geocoder geocoder;
    private double selectedLat, selectedLng;
    List<Address> address;
    String selectedAddress;
    List<Post>list;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_map);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        list=databaseHelper.getAllPosts();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        for(int i=0 ; i<list.size() ; i++) {
            Post post=list.get(i);
            double mLat= Double.parseDouble(post.getmLat());
            double mLng= Double.parseDouble(post.getmLng());
            GetAddress(mLat, mLng);
        }

    }
    private void GetAddress(double mLat, double mLng) {
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
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
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(mLat, mLng);
                    markerOptions.position(latLng).title(selectedAddress);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            gMap = googleMap;
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                            gMap.addMarker(markerOptions).showInfoWindow();
                        }
                    });

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
}