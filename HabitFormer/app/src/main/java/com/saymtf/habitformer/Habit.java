package com.saymtf.habitformer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Habit extends AppCompatActivity {
    public static final String PUBLIC_STATIC_STRING_IDENTIFIER = "com.saymtf.habit.HABITNAME";
    private EditText habitNameText;
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
    }

    public void createTheHabit(View view) {
        String habitName = habitNameText.getText().toString();
        if(!habitName.equals("")) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, habitName);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }else {
            // Display message, Cannot be Empty
            System.out.println("Empty");

        }
    }
}
