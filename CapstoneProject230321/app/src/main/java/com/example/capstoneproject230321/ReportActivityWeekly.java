package com.example.capstoneproject230321;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ReportActivityWeekly extends AppCompatActivity implements OnMapReadyCallback {

    Button mMonthly;
    Button mBack;
    TextView ReportText;

    DatabaseReference ref;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime now = LocalDateTime.now();
    String Today = dtf.format(now);

    int CountOfViolations = 0;

    private GoogleMap mMap;
    MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_weekly);

        mMonthly = findViewById(R.id.montlyReport);
        mBack = findViewById(R.id.backToMenu);
        ReportText = (TextView)findViewById(R.id.weeklyReportText1);

        mMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityReportMonthly();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMenu();
            }
        });

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


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void openActivityReportMonthly(){
        Intent intent = new Intent(this, ReportActivityMonthly.class);
        startActivity(intent);
    }
    public void openActivityMenu(){
        Intent intent = new Intent(this, MenuPageActivity.class);
        startActivity(intent);
    }

    private void collectDataOfUsers(Map<String,Object> users) {

        String dataOfUser = "";

        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            if(Today.equals(String.valueOf(singleUser.get("date")))) {

                CountOfViolations += 1;
                double lat= getRandomLat();
                double longtitude = getRandomLong();
                Log.i("1001", "It is double lat" + lat + "and it is double long" + longtitude);

                pointCreater(lat,longtitude,CountOfViolations);

            }

            //Log.i("1001", String.valueOf(dtf.format(now)));
        }
        dataOfUser = "Total: " + CountOfViolations + " Violations. " + "\n" + "Comman Used Protection: All Of Them.";
        loadDoc(dataOfUser);

    }

    private void loadDoc(String t) {


        ReportText.setMovementMethod(new ScrollingMovementMethod());
        if (t == ""){
            ReportText.setText("There is no log for today!");
        }else {
            ReportText.setText(t);
        }

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.85313062178611, 150.46279444874511);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Machine Center"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.85313062178611, 150.46279444874511), 12.0f));

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-33.85313062178611, 150.46279444874511))
                .radius(4500)
                .strokeColor(Color.RED));

    }

    public double getRandomLat() {
        double min = -33.87678000000;
        double max = -33.82679000000;
        double v = ThreadLocalRandom.current().nextDouble(min,max);
        return v;
    }

    public double getRandomLong() {
        double min = 150.43040500000;
        double max = 150.49709500000;
        double v = ThreadLocalRandom.current().nextDouble(min,max);
        return v;
    }

    public void pointCreater(double lattitude, double longtitude, int count){
        LatLng newPoint = new LatLng(lattitude, longtitude);
        mMap.addMarker(new MarkerOptions().position(newPoint).title( count + ". Violance Point!"));
    }

}


