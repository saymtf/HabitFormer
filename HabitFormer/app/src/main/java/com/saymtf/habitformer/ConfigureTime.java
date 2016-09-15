package com.saymtf.habitformer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ConfigureTime extends AppCompatActivity {
    private int habitTime;
    private int extendedTime;
    private int goalTime;
    private int habitStreak;
    private String userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_time);
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

        Intent intent = getIntent();
        habitTime = intent.getIntExtra(MainActivity.HABIT_TIME, 0);
        goalTime = intent.getIntExtra(MainActivity.HABIT_GOAL_TIME, 0);
        extendedTime = intent.getIntExtra(HabitTimer.HABIT_EXTENDED_TIME_MESSAGE, 0);
        habitStreak = intent.getIntExtra(MainActivity.HABIT_STREAK, 0);
        userInput = "perfect";
    }

//     6,000 -- 1 min
//     300,000 -- 5 min
//     600,000 -- 10 min
//     900,000 -- 15 min
//     1,200,000 -- 20 min
//     1,500,000 -- 25 min
//     1,800,000 -- 30 min
//     3,600,000 -- 60 min
//     7,200,000 - 120 min
//     12,000,000 -- 200 min
//     14,400,000 - 240 min
    protected int configureTime(int time) {
        int newTime;
        if(time >= 12000000) { // 200 Minutes to 240
            newTime = (time/((int)(Math.random() * (12 - 10) + 10)));

            System.out.println("200> The new Time is : " + newTime + " " + (int)(Math.random() * (12 - 10) + 10));
        }else if(time >= 7200000) { // 120 Minutes to 199..

            newTime = (time/((int)(Math.random() * (8 - 6) + 6)));

            System.out.println("120> The new Time is : " + newTime + " " + (int)(Math.random() * (8 - 6) + 6));
        }else if(time >= 3600000) { // 60 Minutes to 119..

            newTime = (time/((int)(Math.random() * (6 - 4) + 4)));

            System.out.println("60> The new Time is : " + newTime + " " + (int)(Math.random() * (6 - 4) + 4));
        }else if(time >=  1200000) { // 20 Minutes to 59..

            newTime = (time/((int)(Math.random() * (4 - 2) + 2)));

            System.out.println("20> The new Time is : " + newTime + " " + (int)(Math.random() * (4 - 2) + 2));
        }else { // >20
            newTime = (time / 2);
        }
        return newTime;
    }

    private void updateTime(String val) {
        int time = extendedTime/2;
        int newTime = 0;
        System.out.println("BEFORE " + time);
        switch(val) {
            case "more":
                newTime += time;
                newTime += (habitTime * 2);
                break;
            case "perfect":
                newTime += time;
                newTime += (habitTime * 1.35);
                break;
            case "less":
                newTime += time;
                newTime += (habitTime * 1.05);
                break;
        }

        habitStreak++; // increase day

        if(habitTime >= goalTime) {
            habitTime = goalTime;
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.HABIT_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("habitTime", newTime);
        editor.apply();

    }

    public void moreTime(View view) {
        TextView configureTimeTextView = (TextView) findViewById(R.id.configure_time);
        configureTimeTextView.setText("Definitly, Need more time.");
        userInput = "more";
    }

    public void perfectTime(View view) {
        TextView configureTimeTextView = (TextView) findViewById(R.id.configure_time);
        configureTimeTextView.setText("The time was good.");
        userInput = "perfect";
    }

    public void lessTime(View view) {
        TextView configureTimeTextView = (TextView) findViewById(R.id.configure_time);
        configureTimeTextView.setText("A little bit harder today");
        userInput = "less";
    }

    public void done(View view) {
        updateTime(userInput);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
