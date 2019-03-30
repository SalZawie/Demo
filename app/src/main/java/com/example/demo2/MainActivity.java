package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText nameEditText;
    private EditText passwordEditText;
    private String strName;
    private String strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = findViewById(R.id.submitButton);
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText   = findViewById(R.id.passwordEditText);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            strName = nameEditText.getText().toString().trim();
            strPassword = passwordEditText.getText().toString().trim();

            if (!strName.isEmpty() && !strPassword.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
            }
        });
    }
}
