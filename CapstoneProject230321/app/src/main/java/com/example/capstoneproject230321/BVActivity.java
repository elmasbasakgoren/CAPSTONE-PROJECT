package com.example.capstoneproject230321;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BVActivity extends AppCompatActivity {

    TextView Today_log;
    TextView Yesterday_log;
    TextView thisWeek_log;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime yesterday = now.minus(1, ChronoUnit.DAYS);

    String Today = dtf.format(now);
    String Yesterday = dtf.format(yesterday);

    DatabaseReference ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_v);

        ref = FirebaseDatabase.getInstance().getReference().child("users").child("suleymanalkan").child("bViolations");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectDataOfUsers((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        Today_log = (TextView)findViewById(R.id.today_data);
        Yesterday_log = (TextView)findViewById(R.id.Yesterday_data);
        thisWeek_log = (TextView)findViewById(R.id.thisWeek_data);

    }

    private void collectDataOfUsers(Map<String,Object> users) {

        String dataOfUserToday = "";
        String dataOfUserYesterday = "";
        String dataOfUserOthers = "";

        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            if(Today.equals(String.valueOf(singleUser.get("date")))) {
                dataOfUserToday = dataOfUserToday + "Date : " + String.valueOf(singleUser.get("date")) + " // Hour : " + String.valueOf(singleUser.get("hour")) + "\n";
            } else if(Yesterday.equals(String.valueOf(singleUser.get("date")))) {
                dataOfUserYesterday = dataOfUserToday + "Date : " + String.valueOf(singleUser.get("date")) + " // Hour : " + String.valueOf(singleUser.get("hour")) + "\n";
            } else{
                dataOfUserOthers = dataOfUserToday + "Date : " + String.valueOf(singleUser.get("date")) + " // Hour : " + String.valueOf(singleUser.get("hour")) + "\n";
            }
            Log.i("1001", String.valueOf(dtf.format(now)));
            Log.i("1002", String.valueOf(dtf.format(yesterday)));
        }

        loadDoc(dataOfUserToday, dataOfUserYesterday, dataOfUserOthers);

    }

    private void loadDoc(String t, String y, String o) {


        Today_log.setMovementMethod(new ScrollingMovementMethod());
        if (t == ""){
            Today_log.setText("There is no log for today!");
        }else {
            Today_log.setText(t);
        }

        Yesterday_log.setMovementMethod(new ScrollingMovementMethod());
        if (y == ""){
            Yesterday_log.setText("There is no log for yesterday!");
        }else {
            Yesterday_log.setText(y);
        }

        thisWeek_log.setMovementMethod(new ScrollingMovementMethod());
        if (o == ""){
            thisWeek_log.setText("There is no log for other days!");
        }else {
            thisWeek_log.setText(o);
        }



    }

}
