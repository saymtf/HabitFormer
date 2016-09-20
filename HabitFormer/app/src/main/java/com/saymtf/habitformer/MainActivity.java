package com.saymtf.habitformer;

import android.app.Activity;
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

public class MainActivity extends AppCompatActivity {
    public static final int STATIC_INTEGER_VALUE = 69;
    public static final String HABIT_NAME = "com.saymtf.habit.A_HABIT_NAME";
    public static final String HABIT_MESSAGE = "com.saymtf.habit.HABIT_MESSAGE";
    public static final String HABIT_TIME = "com.saymtf.habit.HABIT_TIME";
    public static final String HABIT_GOAL_TIME = "com.saymtf.habit.HABIT_GOAL_TIME";
    public static final String HABIT_STREAK = "come.saymtf.habit.HABIT_STREAK";
    public int habitTime;
    public int habitGoalTime;
    public int habitStreak;
    HabitTypes habitTypes;
    private SharedPreferences prefs = null;
    ConfigureTime configureTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.saymtf.habit.firstrun", MODE_PRIVATE);

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

        configureTime = new ConfigureTime();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(HABIT_NAME, 0);
        String habitName = sharedPref.getString("habitName", HABIT_NAME);

        if (!habitName.equals("com.saymtf.habit.A_HABIT_NAME")) {
            // Current User Habit //

            //Create a Layout
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_content);

            habitGoalTime = sharedPref.getInt("habitGoalTime", 0);
            habitTime = sharedPref.getInt("habitTime", 0);
            habitStreak = sharedPref.getInt("habitStreak", 0);


            //Hide Add CreateAHabit Button (Only one at a time)
            Button button = (Button) findViewById(R.id.add_habit_button);
            button.setVisibility(View.GONE);

            //Create Text View
            TextView habitNameTextView = new TextView(this);
            habitNameTextView.setTextSize(30);
            habitNameTextView.setText(habitName);
            habitNameTextView.setId(View.generateViewId());
            habitNameTextView.setOnClickListener(habitNameClicked);

            //Current Habit Streak
            TextView habitStreakTextView = new TextView(this);
            habitStreakTextView.setTextSize(30);
            habitStreakTextView.setText(habitStreak);
            habitStreakTextView.setId(View.generateViewId());


            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            layoutParams.addRule(RelativeLayout.BELOW, R.id.add_habit_button);


            habitNameTextView.setLayoutParams(layoutParams);

            // Add View to layout
            layout.addView(habitNameTextView);
            layout.addView(habitStreakTextView);

        } else {
            Button removeButton = (Button) findViewById(R.id.remove_habit);
            removeButton.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            showIntro();
            prefs.edit().putBoolean("firstrun", false).apply();
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
            showIntro();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == STATIC_INTEGER_VALUE) {
            if(resultCode == Activity.RESULT_OK) {
                String habit = data.getStringExtra(CreateTheHabit.PUBLIC_STATIC_STRING_IDENTIFIER);
                habitGoalTime = data.getIntExtra(CreateTheHabit.PUBLIC_STATIC_INT_IDENTIFIER, 0);
                habitTime = configureTime.configureTime(habitGoalTime);

                // Shared Preferences
                // https://developer.android.com/training/basics/data-storage/shared-preferences.html

                // Save the Info
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(HABIT_NAME, 0);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("habitName", habit);
                editor.putInt("habitGoalTime", habitGoalTime);
                editor.putInt("habitTime", habitTime);
                editor.putInt("habitStreak", 0);
                editor.apply();

                // Update View
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_content);

                //Hide Add CreateAHabit Button (Only one at a time)
                Button addButton = (Button) findViewById(R.id.add_habit_button);
                addButton.setVisibility(View.GONE);

                //Show Remove Button
                Button removeButton = (Button) findViewById(R.id.remove_habit);
                removeButton.setVisibility(View.VISIBLE);


                // Create Text View
               /* TextView test = new TextView(this);
                test.setTextSize(50);
                test.setText(habitTime);
*/
                // Create Text View
                TextView habitTextView = new TextView(this);
                habitTextView.setTextSize(30);
                habitTextView.setText(habit);
                habitTextView.setId(View.generateViewId());
                habitTextView.setOnClickListener(habitNameClicked);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);


                layoutParams.addRule(RelativeLayout.BELOW, R.id.add_habit_button);
                //layoutParams1.addRule(RelativeLayout.BELOW, habitTextView.getId());
                habitTextView.setLayoutParams(layoutParams);
                // test.setLayoutParams(layoutParams1);

                //Add to view
                layout.addView(habitTextView);
                // layout.addView(test);

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
        Intent intent = new Intent(this, CreateTheHabit.class);
        startActivityForResult(intent, STATIC_INTEGER_VALUE);
    }

    public void removeHabit(View view) {
        view.setVisibility(view.GONE);
        //Remove Shared Prefs.

        getApplicationContext().getSharedPreferences(HABIT_TIME, 0).edit().clear().apply();
        getApplicationContext().getSharedPreferences(HABIT_NAME, 0).edit().clear().apply();
        getApplicationContext().getSharedPreferences(HABIT_GOAL_TIME, 0).edit().clear().apply();
        //Update Scene
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }


    private void showIntro() {
        Intent intent = new Intent(this, Introduction.class);
        startActivity(intent);
    }


}
