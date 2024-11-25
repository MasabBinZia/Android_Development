package com.example.midexam;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ManageOperatorsActivity extends AppCompatActivity {

    EditText etOperatorUsername, etOperatorPassword;
    Button btnAddOperator;
    ListView lvOperators;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_operators);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etOperatorUsername = findViewById(R.id.etOperatorUsername);
        etOperatorPassword = findViewById(R.id.etOperatorPassword);
        btnAddOperator = findViewById(R.id.btnAddOperator);
        lvOperators = findViewById(R.id.lvOperators);
        dbHelper = new DBHelper(this);

        btnAddOperator.setOnClickListener(v -> {
            String username = etOperatorUsername.getText().toString().trim();
            String password = etOperatorPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.addFieldOperator(username, password);
            if (success) {
                Toast.makeText(this, "Field Operator added successfully!", Toast.LENGTH_SHORT).show();
                loadOperators();
            } else {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
            }
        });

        loadOperators();
    }

    private void loadOperators() {
        List<String> operators = dbHelper.getAllFieldOperators();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, operators);
        lvOperators.setAdapter(adapter);
    }
}