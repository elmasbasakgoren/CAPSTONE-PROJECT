package com.example.capstoneproject230321;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MakeComplaintsActivity extends AppCompatActivity {

    Button BSendMC;
    EditText MakeComp;
    EditText PhoneNumber;

    DatabaseReference DatabaseRef;
    Task<Void> DatabaseRef2,DatabaseRef3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_complaints);

        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child("complaints");

        MakeComp = findViewById(R.id.editTextComplaints);
        PhoneNumber = findViewById(R.id.editTextEnterPN);

        BSendMC = findViewById(R.id.sendMC);
        BSendMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = MakeComp.getText().toString();
                String phoneNumber = PhoneNumber.getText().toString();
                String uniqueID = UUID.randomUUID().toString();
                if (!phoneNumber.isEmpty()) {
                    DatabaseRef2 = FirebaseDatabase.getInstance().getReference().child("users").child("admin").child("complaints").child(uniqueID).child("message").setValue(message);
                    DatabaseRef3 = FirebaseDatabase.getInstance().getReference().child("users").child("admin").child("complaints").child(uniqueID).child("phoneNumber").setValue(phoneNumber);
                }else{
                    Toast.makeText(MakeComplaintsActivity.this, "You must enter a contact number.", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(MakeComplaintsActivity.this, "Your Message Sent.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
