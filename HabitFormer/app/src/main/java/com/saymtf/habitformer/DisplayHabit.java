package com.saymtf.habitformer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayHabit extends AppCompatActivity {

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
        String habitName = intent.getStringExtra(MainActivity.HABIT_MESSAGE);
        TextView habitNameTextView = new TextView(this);
        habitNameTextView.setText(habitName);
        habitNameTextView.setTextSize(40);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.display_content);
        layout.addView(habitNameTextView);

    }


    public void startButton(View view) {
        System.out.println("Start Button!");
    }
}
