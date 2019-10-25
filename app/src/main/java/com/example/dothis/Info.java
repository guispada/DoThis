package com.example.dothis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt2 = (TextView) findViewById(R.id.txt2);

        String vName = BuildConfig.VERSION_NAME;

        txt2.setText("App Version: " + vName);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Lista.class);
        startActivity(intent);
    }

}
