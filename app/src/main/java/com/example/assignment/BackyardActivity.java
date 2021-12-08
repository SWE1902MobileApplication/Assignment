package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class BackyardActivity extends AppCompatActivity {

    double paperWeight = 0;
    double glassWeight = 0;
    double canWeight = 0;

    TextView loadingText;
    TextView displayText;
    //TODO declare things

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backyard);

        loadingText = findViewById(R.id.byloading);
        displayText = findViewById(R.id.bydisplaytext);
        CalendarUtils cu = ((Global) getApplication()).getUtil();
        //TODO findViewById() things

        FirebaseFirestore.getInstance().collection("user").document(((Global)getApplication()).getLoginUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Map<String, Object>> record = (Map<String, Map<String, Object>>) documentSnapshot.get(cu.selectedDate.toString());
                for (Map<String, Object> m : record.values()) {
                    paperWeight += (Double) m.get("Paper");
                    glassWeight += (Double) m.get("Glass");
                    canWeight += (Double) m.get("Can");
                }
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
        loadingText.setText("");
        double energy = Addition();
        displayText.setText("Total Energy Saved = " + energy + " kwh");
        ImageView IV = findViewById(R.id.imageView);
        if (energy >=0 && energy < 40.88) {
            IV.setImageResource(R.drawable.tree1);
        }
        else if (energy >= 40.88 && energy < 81.76){
            IV.setImageResource(R.drawable.tree2);
        }
        else if (energy >=81.76 && energy < 122.65 ){
            IV.setImageResource(R.drawable.tree3);
        }
        else if (energy >= 122.65 && energy < 163.53){
            IV.setImageResource(R.drawable.tree4);
        }
        else if (energy >= 163.53 && energy < 204.41){
            IV.setImageResource(R.drawable.tree5);
        }
        else if (energy >= 204.41 && energy < 245.29){
            IV.setImageResource(R.drawable.tree6);
        }
        else if (energy >= 245.29 && energy < 286.17){
            IV.setImageResource(R.drawable.tree7);
        }
        else if (energy >= 286.17 && energy < 327.06){
            IV.setImageResource(R.drawable.tree8);
        }
        else if (energy >= 327.06 && energy < 367.94){
            IV.setImageResource(R.drawable.tree9);
        }
        else if (energy >= 367.94 && energy < 408.82){
            IV.setImageResource(R.drawable.tree10);
        }
        else {
            IV.setImageResource(R.drawable.tree10);
            displayText.setText(energy + "kwh");
        }

    }

    private double Addition(){
        //Every 1kg of recycled paper can save 6950watt-hours
        paperWeight = paperWeight * 6.95f;
        //Every 1kg of aluminium can save 219 watt-hours
        canWeight = canWeight * 0.219f;
        //Every 1kg of glass cna save 500 watt-hours
        glassWeight = glassWeight * 0.5f;

        //unit = kwh
        double total = paperWeight + canWeight + glassWeight;

        return total;
    }

}

