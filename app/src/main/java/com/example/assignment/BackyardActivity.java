package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BackyardActivity extends AppCompatActivity {

    double paperWeight = 0;
    double glassWeight = 0;
    double yadayadaWeight = 0;

    TextView loadingText;
    TextView displayText;
    //TODO declare things

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backyard);

        loadingText = findViewById(R.id.byloading);
        displayText = findViewById(R.id.bydisplaytext);
        //TODO findViewById() things

        FirebaseFirestore.getInstance().collection("user").document(((Global)getApplication()).getLoginUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                paperWeight = documentSnapshot.getDouble("paper");
                glassWeight = documentSnapshot.getDouble("glass");
                yadayadaWeight = documentSnapshot.getDouble("yadayada");
                updateTreeProgression();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Backyard:Firestore:getDoc:Fail", e.getLocalizedMessage(), e);
                loadingText.setText("Failed to fetch your progress, please try again later");
            }
        });

    }

    private void updateTreeProgression() {
        // In this function tree progression is guaranteed to be properly set
        // Unlike some async things that make exception go brrrrrrrrr
        loadingText.setEnabled(false);
        displayText.setText(Calculation() * 100 + "%");
        //TODO set text of progression text
        //TODO put animation here if you have
    }

    private double Calculation(){
        //Every 1000kg of recycled paper can save 17 trees
        //The percentage of the user recycled weight = (total weight x (17/1000)) x 100%
        double percentage = (paperWeight * 0.017f * 100);
        //TODO ls yadayadayada...

        return percentage;
    }
}

