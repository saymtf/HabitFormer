package com.saymtf.habitformer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Introduction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

    }
//http://greatist.com/fitness/build-better-you-one-habit-time


    public void nextIntro(View view) {
        Intent intent = new Intent(this, IntroductionTwo.class);
        startActivity(intent);
    }
}
