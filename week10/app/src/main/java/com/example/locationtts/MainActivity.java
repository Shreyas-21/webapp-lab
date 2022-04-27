package com.example.locationtts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView lat, lng, addr;
    Button speak;

    LocationManager locationManager;
    LocationProvider locationProvider;
    LocationListener locationListener;

    String cur_address;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat = findViewById(R.id.latitude);
        lng = findViewById(R.id.longitude);
        speak = findViewById(R.id.speak);
        addr = findViewById(R.id.address);

        lat.setText("Latitude: Loading");
        lng.setText("Longitude: Loading");
        addr.setText("Address: Loading");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat.setText("Latitude: " + location.getLatitude());
                lng.setText("Longitude: " + location.getLongitude());
                Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    Address address = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
                    cur_address = address.getAddressLine(0);
                    addr.setText("Address: " + cur_address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (TextToSpeech.ERROR != 1) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(addr.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please give location permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}