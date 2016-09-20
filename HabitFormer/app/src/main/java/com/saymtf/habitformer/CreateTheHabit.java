package com.saymtf.habitformer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mitchellfenton on 7/23/16.
 * Times are in the 1000ms == 1sec
 * 10000ms = 10sec
 * 60000ms = 1min
 * 600000ms = 10min
 * ...
 */
public class CreateTheHabit extends AppCompatActivity {
    public static final String PUBLIC_STATIC_STRING_IDENTIFIER = "com.saymtf.habit.HABITNAME";
    public static final String PUBLIC_STATIC_ARRAY_IDENTIFIER = "com.saymtf.habit.HABITNAME";
    public static final String PUBLIC_STATIC_INT_IDENTIFIER = "com.saymtf.habit.HABITTIME";
    private EditText habitNameText;
    private int timeValue;
    private int size;
    private HashMap<Integer, String> habitDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_the_habit);

        habitNameText = (EditText) findViewById(R.id.habit_name);
        timeValue = 300000; // initial setup 5 min
        size = 0;
        habitDay = new HashMap<Integer, String>();


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

        TableLayout tl = (TableLayout) findViewById(R.id.create_the_habit);
        TableRow tr = new TableRow(this);

       // Calendar Selector
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for(int i = 0; i < days.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(days[i] + " ");
            textView.setId(days[i].hashCode());
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(3,8,2,0);
            textView.setOnClickListener(highlightDay);
            tr.addView(textView);
        }

        tl.addView(tr);
    }

    public View.OnClickListener highlightDay = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            TextView text =  (TextView) findViewById(view.getId());
            if(text.getCurrentTextColor() == Color.BLACK) {
                text.setTextColor(Color.BLUE);
                System.out.println(text.getText());
                habitDay.put(size, text.getText().toString());
                size++;
            }else {
                text.setTextColor(Color.BLACK);
                habitDay.remove(size);
                size--;
            }
        }
    };

    public void createTheHabit(View view) {
        String habitName = habitNameText.getText().toString();
        if(!habitName.equals("") && size != 0) {
            timeValue = 100000;
            Intent resultIntent = new Intent();
            resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, habitName);
            resultIntent.putExtra(PUBLIC_STATIC_INT_IDENTIFIER, timeValue);
            resultIntent.putExtra(PUBLIC_STATIC_ARRAY_IDENTIFIER, habitDay);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }else {
            // Display message, Cannot be Empty
            System.out.println("Empty");
        }
    }

}
