package com.example.digitalelections.UI.HomePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.digitalelections.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);
        bottomNavigationView = findViewById(R.id.BottomNavigationBar);
        fragmentManager = getSupportFragmentManager();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    fragmentManager.beginTransaction().replace(R.id.frameLayout,HomeFragment.class,null).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.profile) {
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, profileFragment.class,null).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.country) {
                    fragmentManager.beginTransaction().replace(R.id.frameLayout,CountryFragment.class,null).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.city) {
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, CityFragment.class,null).commit();
                    return true;
                }
                return false;
            }
        });

    }
}