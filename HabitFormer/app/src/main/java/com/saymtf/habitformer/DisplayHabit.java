package com.saymtf.habitformer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;




public class DisplayHabit extends AppCompatActivity {
    private String habitName;
    private TextView habitNameTextView;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_habit);
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

        Intent intent = getIntent();
        habitName = intent.getStringExtra(MainActivity.HABIT_MESSAGE);
        habitNameTextView = new TextView(this);
        habitNameTextView.setText(habitName);
        habitNameTextView.setTextSize(80);
        habitNameTextView.setId(View.generateViewId());


        time = intent.getIntExtra(MainActivity.HABIT_TIME, 0);
        System.out.println("Display Habit " + time);
        Button startButton = (Button) findViewById(R.id.start_button);

        setContentView(R.layout.fragment_display_habit);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.display_content);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, habitNameTextView.getId());
        habitNameTextView.setLayoutParams(layoutParams);


        layout.addView(habitNameTextView);
    }

    public void startAction(View view) {
        Intent intent = new Intent(this, HabitTimer.class);
        intent.putExtra(MainActivity.HABIT_MESSAGE, habitName);
        intent.putExtra(MainActivity.HABIT_TIME, time);
        startActivity(intent);
    }

    public void editAction(View view) {
        EditText editText = new EditText(this);
        editText.setHint("Change Habit Name");

        Button removeHabitButton = new Button(this);
        removeHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Go Back and Remove this instance/sharedPref");
            }
        });

    }
}
