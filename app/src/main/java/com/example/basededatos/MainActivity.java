package com.example.basededatos;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText nameEditText, emailEditText;
    Button saveButton, showButton;
    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        saveButton = findViewById(R.id.saveButton);
        showButton = findViewById(R.id.showButton);
        dataTextView = findViewById(R.id.dataTextView);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });
    }

    private void saveData() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        boolean isInserted = myDb.insertData(name, email);

        if (isInserted) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private void showData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No Data Found");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("ID :").append(res.getString(0)).append("\n");
            buffer.append("Name :").append(res.getString(1)).append("\n");
            buffer.append("Email :").append(res.getString(2)).append("\n\n");
        }

        dataTextView.setText(buffer.toString());
    }

    private void showMessage(String title, String message) {
        Toast.makeText(this, title + "\n" + message, Toast.LENGTH_LONG).show();
    }
}
