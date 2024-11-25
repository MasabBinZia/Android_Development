package com.example.midexam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDashboardActivity extends AppCompatActivity {

    Button btnAddStreetLight, btnViewStreetLights, btnManageOperators, btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnAddStreetLight = findViewById(R.id.btnAddStreetLight);
        btnViewStreetLights = findViewById(R.id.btnViewStreetLights);
        btnManageOperators = findViewById(R.id.btnManageOperators);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddStreetLight.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AddStreetLightActivity.class);
            startActivity(intent);
        });

        btnViewStreetLights.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ViewStreetLightsActivity.class);
            startActivity(intent);
        });

        btnManageOperators.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageOperatorsActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}