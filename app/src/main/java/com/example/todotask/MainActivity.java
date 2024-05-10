package com.example.todotask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottomMenu);
        currentFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, currentFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.Tracker:
                        selectedFragment = new AddFragment();
                        break;
                }

                if (selectedFragment != null && selectedFragment != currentFragment) {
                    currentFragment = selectedFragment;
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}