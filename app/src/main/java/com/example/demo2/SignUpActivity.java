package com.example.demo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity
{
    private Button loginButton;
    private Button submitButton;

    private EditText fullName;
    private EditText userEmail;
    private EditText passWord;

    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginButton  = (Button) findViewById(R.id.btnLogin);
        submitButton = (Button) findViewById(R.id.btnSignUp);

        fullName     = (EditText) findViewById(R.id.txtSUName);
        userEmail    = (EditText) findViewById(R.id.txtSUEmail);
        passWord     = (EditText) findViewById(R.id.txtSUPassword);

        alertBuilder = new AlertDialog.Builder(SignUpActivity.this);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!fullName.getText().toString().isEmpty() && !userEmail.getText().toString().isEmpty() && !passWord.getText().toString().isEmpty())
                {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);

                    // TODO
                    // Set UserName & Password fields to Blank.
                }
                else
                {
                    alertBuilder.setMessage("Full Name, Email, and Password are required fields.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                }
                            });

                    AlertDialog alert = alertBuilder.create();
                    alert.setTitle("Alert !!");
                    alert.show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
