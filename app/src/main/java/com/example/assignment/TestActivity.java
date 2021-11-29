package com.example.assignment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.core.app.ActivityOptionsCompat;

import com.example.assignment.ml.InceptionV4Quant1Metadata1;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class TestActivity extends AppCompatActivity{
    InceptionV4Quant1Metadata1 model;
    Button camButton;
    ImageView image;
    TextView text;
    ActivityResultLauncher<Intent> arl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        camButton = findViewById(R.id.testBtn);
        image = findViewById(R.id.testImg);
        text = findViewById(R.id.testText);
        arl = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Bitmap photo = (Bitmap)result.getData().getExtras().get("data");
                            image.setImageBitmap(photo);
                            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            List<Category> probability = null;
                            String preparedStr = "Something went wrong, please try again";
                            try{
                                model = InceptionV4Quant1Metadata1.newInstance(getApplicationContext());
                                TensorImage image = TensorImage.fromBitmap(photo);
                                InceptionV4Quant1Metadata1.Outputs outputs = model.process(image);
                                probability = outputs.getProbabilityAsCategoryList();
                                probability.sort(new Comparator<Category>() {
                                    @Override
                                    public int compare(Category o1, Category o2) {
                                        if(o1.getScore() - o2.getScore() < 0){
                                            return 1;
                                        }else if(o1.getScore() - o2.getScore() > 0){
                                            return -1;
                                        }
                                        return 0;
                                    }
                                });
                            }catch (IOException e) {
                                Log.e(e.toString(), e.getMessage());
                            }
                            if(probability != null){
                                preparedStr = "";
                                for(int i = 0; i < 3; i++){
                                    preparedStr += probability.get(i).getLabel() + " : "+ Float.toString(probability.get(i).getScore()) + "\n";
                                }
                                text.setText(preparedStr);
                                Log.d("Model Out:", probability.get(0).getLabel());
                            }
                            model.close();
                        }
                    }
                });
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
                    //TODO if grant proceed
                }else{
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    arl.launch(cameraIntent);
                }
            }
        });
    }
}