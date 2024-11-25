package com.example.midexam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddStreetLightActivity extends AppCompatActivity {
    EditText etLocation, etOperatorId;
    Button btnAddLight;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_street_light);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etLocation = findViewById(R.id.etLocation);
        etOperatorId = findViewById(R.id.etOperatorId);
        btnAddLight = findViewById(R.id.btnAddLight);
        dbHelper = new DBHelper(this);

        btnAddLight.setOnClickListener(v -> {
            String location = etLocation.getText().toString().trim();
            String operatorIdStr = etOperatorId.getText().toString().trim();

            if (location.isEmpty() || operatorIdStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int operatorId;
            try {
                operatorId = Integer.parseInt(operatorIdStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Operator ID must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.addStreetLight(location, "off", operatorId);
            if (success) {
                Toast.makeText(this, "Street Light added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add Street Light", Toast.LENGTH_SHORT).show();
            }
        });
    }
}