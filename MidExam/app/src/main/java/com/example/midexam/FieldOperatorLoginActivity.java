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

public class FieldOperatorLoginActivity extends AppCompatActivity {

    EditText etOperatorUsername, etOperatorPassword;
    Button btnOperatorLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_field_operator_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etOperatorUsername = findViewById(R.id.etOperatorUsername);
        etOperatorPassword = findViewById(R.id.etOperatorPassword);
        btnOperatorLogin = findViewById(R.id.btnOperatorLogin);
        dbHelper = new DBHelper(this);

        btnOperatorLogin.setOnClickListener(v -> {
            String username = etOperatorUsername.getText().toString().trim();
            String password = etOperatorPassword.getText().toString().trim();

            if (dbHelper.validateFieldOperator(username, password)) {
                Intent intent = new Intent(FieldOperatorLoginActivity.this, FieldOperatorDashboardActivity.class);
                intent.putExtra("operatorUsername", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid Operator Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }
}