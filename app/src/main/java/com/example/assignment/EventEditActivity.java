package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//TODO get recycled weight and items of each user
//TODO after user click the save button, the list of items will be saved and update in database
//TODO save user tree progression



public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    CalendarUtils cu;
    Chip chip1,chip2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cu = ((Global) getApplication()).getUtil();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        final ListView lv = (ListView) findViewById(R.id.lv);
        final Button btn = (Button) findViewById(R.id.btn);
        String[] LABEL = new String[]{

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LABEL);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.label);
        textView.setAdapter(adapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.RecycleItem_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter2);

        final List<String> Label_list = new ArrayList<String>(Arrays.asList(LABEL));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, Label_list);

        lv.setAdapter(arrayAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new Items to List
                Label_list.add("paper");
                Label_list.add("can");
                /*
                    notifyDataSetChanged ()
                        Notifies the attached observers that the underlying
                        data has been changed and any View reflecting the
                        data set should refresh itself.
                 */
                arrayAdapter.notifyDataSetChanged();
            }
        });


        /*ElegantNumberButton button = (ElegantNumberButton) findViewById(R.id.number_button);
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
        });*/


    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, cu.selectedDate);
        Event.eventsList.add(newEvent);
        startActivity(new Intent(this, BackyardActivity.class));
    }

}