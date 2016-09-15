package com.saymtf.habitformer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by mitchellfenton on 7/23/16.
 * Times are in the 1000ms == 1sec
 * 10000ms = 10sec
 * 60000ms = 1min
 * 600000ms = 10min
 * ...
 */
public class Habit extends AppCompatActivity {
    public static final String PUBLIC_STATIC_STRING_IDENTIFIER = "com.saymtf.habit.HABITNAME";
    public static final String PUBLIC_STATIC_INT_IDENTIFIER = "com.saymtf.habit.HABITTIME";
    private EditText habitNameText;
    private int timeValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        habitNameText = (EditText) findViewById(R.id.habit_name);
        timeValue = 300000; // initial setup 5 min

        NumberPicker np = (NumberPicker) findViewById(R.id.habit_time);
        np.setMaxValue(240);
        np.setMinValue(5);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                timeValue = newVal*60000; // 60000 -- 1 min
            }

        });

        TableLayout layout = (TableLayout) findViewById(R.id.habit);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
        rowParams.width = TableLayout.LayoutParams.WRAP_CONTENT;
        rowParams.height = TableLayout.LayoutParams.WRAP_CONTENT;
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(rowParams);
        //Calendar Selector
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for(int i = 0; i < days.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(days[i]);
            textView.setText(days[i]);
            textView.setId(days[i].hashCode());
            tr.addView(textView);
            // Add View to layout
        }
        layout.addView(tr);
    }

    public void createTheHabit(View view) {
        String habitName = habitNameText.getText().toString();
        if(!habitName.equals("")) {
            timeValue = 100000;
            Intent resultIntent = new Intent();
            resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, habitName);
            resultIntent.putExtra(PUBLIC_STATIC_INT_IDENTIFIER, timeValue);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }else {
            // Display message, Cannot be Empty
            System.out.println("Empty");
        }
    }
}
