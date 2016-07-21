package com.saymtf.habitformer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int STATIC_INTEGER_VALUE = 69;
    public static final String PUBLIC_STATIC_STRING_IDENTIFIER = "com.saymtf.habit.HABITNAME";
    public static final String HABIT_NAME = "com.saymtf.habit.A_HABIT_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(HABIT_NAME, 0);
        String habitName = sharedPref.getString("habitName", HABIT_NAME);
        if (!habitName.equals(HABIT_NAME)) {
            TextView habitNameTextView = new TextView(this);
            habitNameTextView.setTextSize(50);
            habitNameTextView.setText(habitName);

            RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
            layout.addView(habitNameTextView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == STATIC_INTEGER_VALUE) {
            if(resultCode == Activity.RESULT_OK) {
                String habit = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
                // Update View
                TextView habitTextView = new TextView(this);
                habitTextView.setTextSize(40);
                habitTextView.setText(habit);

                RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
                layout.addView(habitTextView);

                // Save The Information using
                // https://developer.android.com/training/basics/data-storage/shared-preferences.html
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(HABIT_NAME, 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("habitName", habitTextView.getText().toString());
                editor.apply();

            }
        }

    }

    public void addHabit(View view) {
        Intent intent = new Intent(this, Habit.class);
        startActivityForResult(intent, STATIC_INTEGER_VALUE);
    }

}
