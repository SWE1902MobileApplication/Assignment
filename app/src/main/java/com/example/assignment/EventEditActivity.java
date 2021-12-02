package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.chip.Chip;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV;
    CalendarUtils cu;
    Chip chip1,chip2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        cu = ((Global)getApplication()).getUtil();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        eventDateTV.setText("Date: " + cu.formattedDate(cu.selectedDate));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LABEL );
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.label);
        textView.setAdapter(adapter);

        ElegantNumberButton button = (ElegantNumberButton) findViewById(R.id.number_button);
        button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = button.getNumber();
            }
        });

        chip1 = findViewById(R.id.chipLabel);
        chip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventEditActivity.this, "Action Completed", Toast.LENGTH_SHORT).show();
            }
        });

        chip2 = findViewById(R.id.chipWeight);
        chip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventEditActivity.this, "Action Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final String[] LABEL = new String[] {
            "Paper", "Plastic bag", "Newspaper", "Box", "Iron", "Glasses"
    };

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, cu.selectedDate);
        Event.eventsList.add(newEvent);
        //startActivity(new Intent(EventEditActivity.this, .class));
        finish();
    }

}