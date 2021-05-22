package com.example.capstoneproject230321;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TOTOActivity extends AppCompatActivity {

    Button mTurnOn;
    Button mTurnOff;

    DatabaseReference TODatabaseRef1,TODatabaseRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_o_t_o);

        TODatabaseRef1 = FirebaseDatabase.getInstance().getReference().child("users").child("suleymanalkan").child("appStatus");
        TODatabaseRef2 = FirebaseDatabase.getInstance().getReference().child("users").child("suleymanalkan").child("systemStatus");

        mTurnOff = findViewById(R.id.turnOff);
        mTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TODatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data = snapshot.getValue().toString();
                            if (data.equals("off")){
                                Toast.makeText(TOTOActivity.this, "App Status Already -OFF- ", Toast.LENGTH_SHORT).show();
                            }else if (data.equals("on")){
                                TODatabaseRef1.setValue("off");
                                freeze(1000);
                                TODatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            String data2 = dataSnapshot.getValue().toString();
                                            if (data2.equals("off")) {
                                                Toast.makeText(TOTOActivity.this, "System  OFF", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(TOTOActivity.this, "Waiting...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        mTurnOn = findViewById(R.id.turnOn);
        mTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TODatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String data = snapshot.getValue().toString();
                            if (data.equals("on")){
                                Toast.makeText(TOTOActivity.this, "App Status Already -ON- ", Toast.LENGTH_SHORT).show();
                            }else if (data.equals("off")){
                                TODatabaseRef1.setValue("on");
                                freeze(1000);
                                TODatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            String data2 = dataSnapshot.getValue().toString();
                                            if (data2.equals("on")) {
                                                Toast.makeText(TOTOActivity.this, "System  ON", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(TOTOActivity.this, "Waiting...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    public void freeze(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
