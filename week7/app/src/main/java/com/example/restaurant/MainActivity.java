package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.edit);
        lv = findViewById(R.id.item_list);

        populateItems(lv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });
    }

    void populateItems(ListView lv) {
        DBHandler db = new DBHandler(MainActivity.this, "restaurant.db", null, 1);
        List<String> items = db.getItems();
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(ad);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateItems(lv);
    }
}