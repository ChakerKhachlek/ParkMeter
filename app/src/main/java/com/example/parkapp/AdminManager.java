package com.example.parkapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.parkapp.fragments.HistoryFragment;
import com.example.parkapp.fragments.PaperManagementFragment;
import com.example.parkapp.fragments.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);

        BottomNavigationView bottomnav=findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListner);

        //default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PaperManagementFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch(item.getItemId()) {
                case (R.id.nav_paper):
                    selectedFragment=new PaperManagementFragment();
                    break;
                case (R.id.nav_statistics):
                    selectedFragment=new StatisticsFragment();
                    break;
                case (R.id.nav_history):
                    selectedFragment=new HistoryFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };
}