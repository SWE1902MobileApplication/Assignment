package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV;
    CalendarUtils cu;

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
        finish();
    }

}