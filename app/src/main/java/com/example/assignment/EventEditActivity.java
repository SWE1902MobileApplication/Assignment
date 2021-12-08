package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//TODO get recycled weight and items of each user
//TODO after user click the save button, the list of items will be saved and update in database
//TODO save user tree progression

//TODO add a button to remove from list
//TODO amount?
//TODO why there are earth and venus and things? I can recycle planets???
//FIXME no matter what you choose in list, paper and can will both be added

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    CalendarUtils cu;
    LinearLayout ll, newUnit;
    EditText et;
    ImageButton closebtn;
    ViewGroup.LayoutParams spinnerLayoutParams, llLayoutParam, etLayoutParam, closebtnLayoutParam;

    Chip chip1,chip2;

    double paperWeight = 0;
    double glassWeight = 0;
    double canWeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cu = ((Global) getApplication()).getUtil();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        eventNameET = findViewById(R.id.eventNameET);

        ll = findViewById(R.id.evenEditLL);

        newUnit = findViewById(R.id.newListUnit);
        llLayoutParam = newUnit.getLayoutParams();

        Spinner spinner = findViewById(R.id.spinner);
        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.RecycleItem_array, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinnerLayoutParams = spinner.getLayoutParams();

        et = findViewById(R.id.editTextNumberDecimal);
        etLayoutParam = et.getLayoutParams();

        closebtn = findViewById(R.id.deletebtn);
        closebtnLayoutParam = closebtn.getLayoutParams();

        Button btn = findViewById(R.id.newListbtn);

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup) v.getParent().getParent()).removeView(((View) v.getParent()));
            }
        });
        
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner newSpinner = new Spinner(EventEditActivity.this);
                newSpinner.setAdapter(adapter);
                EditText newEt = new EditText(EventEditActivity.this);
                newEt.setInputType(8194);
                ImageButton newDelete = new ImageButton(EventEditActivity.this);
                newDelete.setImageResource(R.drawable.ic_baseline_close_24);
                newDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ViewGroup) v.getParent().getParent()).removeView(((View) v.getParent()));
                    }
                });
                LinearLayout newLL = new LinearLayout(EventEditActivity.this);
                newLL.addView(newSpinner, spinnerLayoutParams);
                newLL.addView(newEt, etLayoutParam);
                newLL.addView(newDelete, closebtnLayoutParam);

                ll.addView(newLL, ll.getChildCount() - 2, llLayoutParam);
            }
        });
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        if(eventName.equals("")){
            eventNameET.setError("Cannot be empty");
            eventNameET.requestFocus();
            return;
        }
        //FIXME will overwrite
        Map<String, Object> m = new HashMap<>();
        m.put("Paper", 0.0);
        m.put("Glass", 0.0);
        m.put("Can", 0.0);
        for(int i = 0; i < ll.getChildCount(); i++){
            View v = ll.getChildAt(i);
            if(v instanceof LinearLayout){
                String key = ((Spinner) ((LinearLayout) v).getChildAt(0)).getSelectedItem().toString();
                double c = (double) m.get(key);
                String val = (((EditText) ((LinearLayout) v).getChildAt(1)).getText().toString());
                c += val.equals("")? 0: Double.parseDouble(val);
                m.replace(key, c);
            }
        }
        Map<String, Map<String, Object>> item = new HashMap<>();
        item.put(eventName, m);
        Map<String, Map<String, Map<String, Object>>> record= new HashMap<>();
        record.put(cu.selectedDate.toString(), item);
        FirebaseFirestore.getInstance().collection("user").document(((Global) getApplication()).getLoginUser().getUid()).set(record, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(EventEditActivity.this, BackyardActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EventEditActivity.this, "Failed to update event record, please try again later", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}