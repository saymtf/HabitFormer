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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static final int STATIC_INTEGER_VALUE = 69;
    public static final String HABIT_NAME = "com.saymtf.habit.A_HABIT_NAME";
    public static final String HABIT_MESSAGE = "com.saymtf.habit.HABIT_MESSAGE";
    public static final String HABIT_TIME = "com.saymtf.habit.HABIT_TIME";
    public int habitTime;
    HabitTypes habitTypes;

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
        if (!habitName.equals("com.saymtf.habit.A_HABIT_NAME")) {
            //Create a Layout
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_content);

            habitTime = sharedPref.getInt("habitTime", 0);


            //Hide Add Habit Button (Only one at a time)
            Button button = (Button) findViewById(R.id.add_habit_button);
            button.setVisibility(View.GONE);

               //Create Text View
            TextView habitNameTextView = new TextView(this);
            habitNameTextView.setTextSize(30);
            habitNameTextView.setText(habitName);
            habitNameTextView.setId(View.generateViewId());
            habitNameTextView.setOnClickListener(habitNameClicked);


                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                );

                layoutParams.addRule(RelativeLayout.BELOW, R.id.add_habit_button);


                habitNameTextView.setLayoutParams(layoutParams);

             // Add View to layout
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
                String habit = data.getStringExtra(Habit.PUBLIC_STATIC_STRING_IDENTIFIER);
                habitTime = data.getIntExtra(Habit.PUBLIC_STATIC_INT_IDENTIFIER, 0);

                // Shared Preferences
                // https://developer.android.com/training/basics/data-storage/shared-preferences.html

                // Save the Info
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(HABIT_NAME, 0);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("habitName", habit);
                editor.putInt("habitTime", habitTime);
                editor.apply();

                // Update View
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_content);

                //Hide Add Habit Button (Only one at a time)
                Button button = (Button) findViewById(R.id.add_habit_button);
                button.setVisibility(View.GONE);

                // Create Text View
                TextView habitTextView = new TextView(this);
                habitTextView.setTextSize(30);
                habitTextView.setText(habit);
                habitTextView.setId(View.generateViewId());
                habitTextView.setOnClickListener(habitNameClicked);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.addRule(RelativeLayout.BELOW, R.id.add_habit_button);
                habitTextView.setLayoutParams(layoutParams);
                //Add to view
                layout.addView(habitTextView);

            }
        }

    }

    public View.OnClickListener habitNameClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(view.getContext(), DisplayHabit.class);
            TextView habitName = (TextView) findViewById(view.getId());
            String name = habitName.getText().toString();
            intent.putExtra(HABIT_MESSAGE, name);
            intent.putExtra(HABIT_TIME, habitTime);
            startActivity(intent);

        }
    };

    public void addHabit(View view) {
        Intent intent = new Intent(this, Habit.class);
        startActivityForResult(intent, STATIC_INTEGER_VALUE);
    }

}
