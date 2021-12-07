package com.example.assignment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BackyardFragment extends AppCompatActivity {

    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FirebaseAuth auth;
    private Map<String, ArrayList> progression;

    //TODO declare button, text, etc...


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_backyard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //updateProgression();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        **********************DATABASE REFERENCE FOR USER PROGRESSION***************************
        /*progression = new HashMap<String, ArrayList>();
        db.collection("Users").document("Username").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.e("AAA", task.getResult().getId());
                    }
                });*/


        //     addListenerForSingleValueEvent(new ValueEventListener() {
        // @Override
        //public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        //    String userCalorieSuggestion = String.valueOf((dataSnapshot.child("daily recycled weight").getValue()));
        //    userRequiredCalorie.setText((userCalorieSuggestion + "kg"));

        //}


        //TODO get button, text, etc

        //TODO submit button add listener, firebase things goes inside listener

    /*}

    private void init() {
        auth = FirebaseAuth.getInstance();
    }

}


private void updateProgression(double c) {

        DatabaseReference rootRef = FirebaseFirestore.getInstance().getReference();
        DatabaseReference nameRef = rootRef.child("UsersRecords").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double count = 0;
                for (DataSnapshot foodTypeSnapshot: dataSnapshot.getChildren()) {
                    for (DataSnapshot recordSnapshot: foodTypeSnapshot.getChildren()) {
                        double recycledWeight = Double.valueOf(recordSnapshot.child("foodCalorie").getValue(String.class));
                        count = count + recycledWeight;
                        BreakIterator userProgression = null;
                        userProgression.setText((count  +"kg"));


                        DatabaseReference myHistoryRef = FirebaseDatabase.getInstance().
                                getReference("History").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Total Recycled Weight");

                        Map<String, Object> values = new HashMap<>();
                        values.put("totalRecycledWeight", count);

                        final double finalCount = count;
                        myHistoryRef.updateChildren(values).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                updateProgression(finalCount);
                            }
                        });
                    }
                }
                Log.d("TAG", count + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    ImageView demoImage = (ImageView) getView().findViewById(R.id.simpleImageView);
    int imagesToShow[] = { R.drawable.tree1, R.drawable.tree2,R.drawable.tree3 };

    animate(demoImage, imagesToShow, 0,false);



    private void animate(final ImageView imageView, final int images[], final int imageIndex, final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever == true){
                        animate(imageView, images, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }*/
}