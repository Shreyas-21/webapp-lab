package com.example.bankotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button send;
    final private int REQUEST_SEND_SMS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        send = findViewById(R.id.button);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS }, REQUEST_SEND_SMS);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pw = password.getText().toString();
                Random random = new Random();
                String otp = String.format("%04d", random.nextInt(10000));

                DBHandler db = new DBHandler(MainActivity.this, "bank.db", null, 1);

                if (db.checkUserExists(name)) {
                    if (db.checkUserVerified(name)) {
                        Toast.makeText(MainActivity.this, "User verified", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, VerifyOTP.class);
                        intent.putExtra("USER_NAME", name);
                        startActivity(intent);
                    }
                } else {
                    db.addNewUser(name, pw, otp);

                    SmsManager sms = SmsManager.getDefault();
                    String msg = "OTP for Bank Verification is: " + otp;
                    sms.sendTextMessage("9840792789", null, msg, null, null);

                    Intent intent = new Intent(MainActivity.this, VerifyOTP.class);
                    intent.putExtra("USER_NAME", name);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "OTP Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please give permission to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}