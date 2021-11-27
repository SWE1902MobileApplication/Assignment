package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNav=findViewById(R.id.nav_view);
        bottomNav.setOnItemSelectedListener(navListener);
    }


    private BottomNavigationView.OnItemSelectedListener navListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_info:
                    selectedFragment = new InfomationFragment();
                    break;
                case R.id.navigation_backyard:
                    selectedFragment = new BackyardFragment();
                    break;
                case R.id.navigation_map:
                    selectedFragment = new MapFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            // TODO bottomNav.getMenu().getItem(item.getGroupId()).setChecked(true);
            //TODO first fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,selectedFragment).commit();
            //id fragment_container???
            return false;
        }
    };
}