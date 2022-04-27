package com.example.bankotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VerifyOTP extends AppCompatActivity {
    EditText otp;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        otp = findViewById(R.id.otp);
        verify = findViewById(R.id.verify_otp);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp_text = otp.getText().toString();
                String name = getIntent().getStringExtra("USER_NAME");

                DBHandler db = new DBHandler(VerifyOTP.this, "bank.db", null, 1);
                if (db.tryVerifyUser(name, otp_text)) {
                    Toast.makeText(VerifyOTP.this, "User verified", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VerifyOTP.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerifyOTP.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}