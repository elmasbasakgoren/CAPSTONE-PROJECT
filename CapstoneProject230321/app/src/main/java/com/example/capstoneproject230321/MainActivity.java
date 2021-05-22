package com.example.capstoneproject230321;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity<Login> extends AppCompatActivity {

    VideoView videoView;
    public int minPASSWORD_LENGTH = 8;
    public int maxPASSWORD_LENGTH = 48;

    EditText mUserName;
    EditText mPassword;
    Button mLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.backvideo);

        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoView.start();

        mUserName = findViewById(R.id.editTextUserName);
        mPassword = findViewById(R.id.editTextPassword);
        mLogin = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = mUserName.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Error! User name or password is empty!", Toast.LENGTH_SHORT).show();
                    //openActivityMenuPage();
                } else {
                     loginUser(userName , password);
                }
            }
        });
    }

    private void loginUser(String userName, String password) {

        mAuth.signInWithEmailAndPassword(userName , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    openActivityMenuPage();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error! User name or password is incorrect.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openActivityMenuPage(){
        Intent intent = new Intent(this, MenuPageActivity.class);
        startActivity(intent);
    }


    public static boolean is_Valid_Password(String userName, String password, int minPASSWORD_L, int maxPASSWORD_L ) {


        //boolean passwordRequirementsControl = is_Valid_Password(userName,password,minPASSWORD_LENGTH,maxPASSWORD_LENGTH);

        int charCount = 0;
        int numCount = 0;

        if(TextUtils.isEmpty(userName)){
            //userName.setError("Email is required.");
            Log.e(userName,"******User Name is empty.******" );
            return false;
        }

        if(TextUtils.isEmpty(password)){
            //userName.setError("Email is required.");
            Log.e(password,"******Password is empty.******" );
            return false;
        }

        if(password.length() < minPASSWORD_L && password.length() > maxPASSWORD_L ){
            //userName.setError("Email is required.");
            Log.e(String.valueOf(password),"******Password is empty.******" );
            return false;
        }

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }

        if(numCount < 1 && charCount < 1 ){
            //userName.setError("Email is required.");
            Log.e(String.valueOf(password),"******Password must have at least one number and one character.******" );
            return false;
        }

        return true;
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

}
