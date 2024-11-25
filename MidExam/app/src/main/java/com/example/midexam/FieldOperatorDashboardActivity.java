package com.example.midexam;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class FieldOperatorDashboardActivity extends AppCompatActivity {

    ListView lvAssignedLights;
    DBHelper dbHelper;
    String operatorUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_field_operator_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvAssignedLights = findViewById(R.id.lvAssignedLights);
        dbHelper = new DBHelper(this);

        operatorUsername = getIntent().getStringExtra("operatorUsername");
        if (operatorUsername == null) {
            Toast.makeText(this, "Error: Operator username not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadAssignedLights();
    }

    private void loadAssignedLights() {
        List<String> assignedLights = dbHelper.getAssignedStreetLights(operatorUsername);

        if (assignedLights.isEmpty()) {
            Toast.makeText(this, "No assigned street lights!", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignedLights);
            lvAssignedLights.setAdapter(adapter);

            lvAssignedLights.setOnItemClickListener((parent, view, position, id) -> {
                String lightDetails = assignedLights.get(position);

                String[] parts = lightDetails.split(":");
                if (parts.length == 3) {
                    int lightId = Integer.parseInt(parts[0]);
                    String currentStatus = parts[2].trim();

                    String newStatus = currentStatus.equals("on") ? "off" : "on";
                    boolean success = dbHelper.toggleStreetLightStatus(lightId, newStatus);

                    if (success) {
                        Toast.makeText(this, "Status toggled successfully!", Toast.LENGTH_SHORT).show();
                        loadAssignedLights();
                    } else {
                        Toast.makeText(this, "Failed to toggle status", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Invalid light details format", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}