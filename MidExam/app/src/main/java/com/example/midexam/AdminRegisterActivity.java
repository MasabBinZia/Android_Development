package com.example.midexam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminRegisterActivity extends AppCompatActivity {

    EditText etAdminRegisterUsername, etAdminRegisterPassword;
    Button btnAdminRegister;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etAdminRegisterUsername = findViewById(R.id.etAdminRegisterUsername);
        etAdminRegisterPassword = findViewById(R.id.etAdminRegisterPassword);
        btnAdminRegister = findViewById(R.id.btnAdminRegister);
        dbHelper = new DBHelper(this);

        btnAdminRegister.setOnClickListener(v -> {
            String username = etAdminRegisterUsername.getText().toString().trim();
            String password = etAdminRegisterPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.addAdmin(username, password);
            if (success) {
                Toast.makeText(this, "Admin registered successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminRegisterActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Username already exists. Try another.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}