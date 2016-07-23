package com.saymtf.habitformer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ConfigureTime extends AppCompatActivity {

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
    }

    public void moreTime(View view) {
        TextView configureTimeTextView = (TextView) findViewById(R.id.configure_time);
        configureTimeTextView.setText("We will improve your habit by adding more time");
    }

    public void perfectTime(View view) {
        TextView configureTimeTextView = (TextView) findViewById(R.id.configure_time);
        configureTimeTextView.setText("Perfect Time");
    }

    public void lessTime(View view) {
        TextView configureTimeTextView = (TextView) findViewById(R.id.configure_time);
        configureTimeTextView.setText("Less Time");


    }
}
