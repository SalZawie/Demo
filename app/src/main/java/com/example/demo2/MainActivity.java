package com.example.demo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Button loginButton;
    private Button signUpButton;

    private EditText userName;
    private EditText passWord;

    AlertDialog.Builder alertBuilder;

   @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton  = (Button) findViewById(R.id.btnLogin);
        signUpButton = (Button) findViewById(R.id.btnSignUp);

        userName     = (EditText) findViewById(R.id.txtEmail);
        passWord     = (EditText) findViewById(R.id.txtPassword);

        alertBuilder = new AlertDialog.Builder(MainActivity.this);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
           @Override
           public void onClick(View view)
           {
             if (!userName.getText().toString().isEmpty() && !passWord.getText().toString().isEmpty())
             {
                  Intent intent = new Intent(MainActivity.this, SearchPageActivity.class);
                  startActivity(intent);

                  // TODO
                  // Set UserName & Password fields to Blank.
             }
             else
             {
                 alertBuilder.setMessage("Invalid UserName or Password")
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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
