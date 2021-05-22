package com.example.capstoneproject230321;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuPageActivity extends AppCompatActivity {

    Button mBorderViolation;
    Button mTurnOnTurnOff;
    Button mMakeComplaints;
    Button mReport;
    Button mLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        mBorderViolation = findViewById(R.id.buttonBorderViolation);

        mBorderViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityBorderViolation();
            }
        });

        mTurnOnTurnOff = findViewById(R.id.buttonTOTO);

        mTurnOnTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityTurnOnTurnOff();
            }
        });

        mMakeComplaints = findViewById(R.id.buttonMakeComplaints);

        mMakeComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMakeComplaints();
            }
        });

        mReport = findViewById(R.id.buttonReport);

        mReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityReport();
            }
        });

        mLogOut = findViewById(R.id.buttonLogOut);

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityLogOut();
            }
        });
    }

    public void openActivityBorderViolation(){
        Intent intent = new Intent(this, BVActivity.class);
        startActivity(intent);
    }

    public void openActivityTurnOnTurnOff(){
        Intent intent = new Intent(this, TOTOActivity.class);
        startActivity(intent);
    }

    public void openActivityMakeComplaints(){
        Intent intent = new Intent(this, MakeComplaintsActivity.class);
        startActivity(intent);
    }

    public void openActivityReport(){
        Intent intent = new Intent(this, ReportActivityWeekly.class);
        startActivity(intent);
    }

    public void openActivityLogOut(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
