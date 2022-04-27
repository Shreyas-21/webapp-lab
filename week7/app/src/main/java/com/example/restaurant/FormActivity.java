package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {
    Button add, remove;
    EditText inputItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);

        inputItem = findViewById(R.id.itemName);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
                finish();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem();
                finish();
            }
        });
    }

    void addItem() {
        String item = inputItem.getText().toString();
        DBHandler db = new DBHandler(FormActivity.this, "restaurant.db", null, 1);
        db.addEntry(item);
    }

    void removeItem() {
        String item = inputItem.getText().toString();
        DBHandler db = new DBHandler(FormActivity.this, "restaurant.db", null, 1);
        db.removeEntry(item);
    }
}