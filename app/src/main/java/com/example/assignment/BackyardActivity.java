package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
//TODO get percentage of tree progression

public class BackyardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backyard);


    }

    private float Addition(float paperWeight, float canWeight, float glassWeight){
        paperWeight = paperWeight * 0.017f;

        float total = paperWeight + canWeight + glassWeight;

        return total;
    }

    private float Calculation(float totalweight){
        //Every 1000kg of recycled paper can save 17 trees
        //The percentage of the user recycled weight = (total weight x (17/1000)) x 100%
        float percentage = (totalweight * 100);

        return percentage;
    }

}

