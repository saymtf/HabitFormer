package com.saymtf.habitformer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class HabitTimer extends AppCompatActivity {
    public TextView mTextField;
    public RelativeLayout layout;
    private Button finishButton;
    private Button continueHabit;
    private int habitTime;
    public static final String HABIT_EXTENDED_TIME_MESSAGE = "com.saymtf.habit.HABIT_TIME_MESSAGE";
    /* TIMER */
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    private CountDownTimer timer;
    /* EOF */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_timer);
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

        setContentView(R.layout.fragment_habit_timer);
        layout = (RelativeLayout) findViewById(R.id.habit_timer);


        Intent intent = getIntent();
        String habitName = intent.getStringExtra(MainActivity.HABIT_MESSAGE);
        habitTime = intent.getIntExtra(MainActivity.HABIT_TIME, 0);

        TextView habitNameTextView = new TextView(this);
        habitNameTextView.setText(habitName);
        habitNameTextView.setTextSize(60);
        habitNameTextView.setId(View.generateViewId());

        RelativeLayout.LayoutParams layoutParamsHabitName = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParamsHabitName.addRule(RelativeLayout.CENTER_HORIZONTAL, habitNameTextView.getId());
        habitNameTextView.setLayoutParams(layoutParamsHabitName);

        int time = intent.getIntExtra(MainActivity.HABIT_TIME, 0);
        System.out.println("Habit Timer " +  time);

        mTextField = new TextView(this);
        mTextField.setTextSize(50);
        mTextField.setId(View.generateViewId());


        RelativeLayout.LayoutParams layoutParamsTimer = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParamsTimer.addRule(RelativeLayout.CENTER_IN_PARENT, mTextField.getId());
        mTextField.setLayoutParams(layoutParamsTimer);


        timer = new CountDownTimer(time, 1000) {
            public void onTick(long miliseconds) {

                int seconds = (int) (miliseconds / 1000);
                System.out.println(seconds);
                int minutes = seconds / 60;
                System.out.println(minutes);
                seconds = seconds % 60;
                String timeText = minutes + ":" + String.format("%02d", seconds);
                mTextField.setText(timeText);

            }

            public void onFinish() {

                mTextField.setText("You're All Done!");
                createButton();
            }

        }.start();

        layout.addView(habitNameTextView);
        layout.addView(mTextField);
    }

    @Override
    protected void onStop() {
        super.onStop();

        myHandler.removeCallbacks(updateTimerMethod);
        timer.cancel();
    }


    public void createButton() {

        //Continue
        continueHabit = new Button(this);
        continueHabit.setText("Continue Working..");
        continueHabit.setOnClickListener(continueHabitClickListener);
        continueHabit.setId(View.generateViewId());

        RelativeLayout.LayoutParams layoutParamsContinue = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParamsContinue.addRule(RelativeLayout.BELOW, mTextField.getId());
        continueHabit.setLayoutParams(layoutParamsContinue);

        //Finish
        finishButton = new Button(this);
        finishButton.setText("Finished");
        finishButton.setOnClickListener(finishClickListener);
        finishButton.setId(View.generateViewId());


        RelativeLayout.LayoutParams layoutParamsFinishButton = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParamsFinishButton.addRule(RelativeLayout.BELOW, continueHabit.getId());
        finishButton.setLayoutParams(layoutParamsFinishButton);

        layout.addView(continueHabit);
        layout.addView(finishButton);
    }


    View.OnClickListener continueHabitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            continueHabit.setVisibility(v.GONE);
            startTime = SystemClock.uptimeMillis();
            myHandler.postDelayed(updateTimerMethod, 0);


        }
    };

    View.OnClickListener finishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timeSwap += timeInMillies;
            myHandler.removeCallbacks(updateTimerMethod);
            int extraTime = (int) timeSwap / 1000;

            Intent intent = new Intent(v.getContext(), ConfigureTime.class);
            intent.putExtra(HABIT_EXTENDED_TIME_MESSAGE, extraTime);
            intent.putExtra(MainActivity.HABIT_TIME, habitTime);
            startActivity(intent);

        }
    };


    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            mTextField.setText("" + minutes + ":"
                    + String.format("%02d", seconds));
            myHandler.postDelayed(this, 0);
        }

    };
}
