package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private ImageButton nnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNav=findViewById(R.id.nav_view);
        bottomNav.setOnItemSelectedListener(navListener);

        nnBtn = findViewById(R.id.nnBtn);
        nnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TestActivity.class));
            }
        });
    }


    private BottomNavigationView.OnItemSelectedListener navListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            int id = 0;

            switch (item.getItemId()){
                case R.id.navigation_info:
                    selectedFragment = new InfomationFragment();
                    id = 0;
                    break;
                case R.id.navigation_map:
                    selectedFragment = new MapFragment();
                    id = 1;
                    break;
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    id = 2;
                    break;
                /*case R.id.navigation_backyard:
                    selectedFragment = new CalendarActivity();
                    id = 3;
                    break;*/
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    id = 4;
                    break;
            }
            bottomNav.getMenu().getItem(id).setChecked(true);
            // TODO first fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,selectedFragment).commit();
            //id fragment_container???
            return false;
        }
    };
}