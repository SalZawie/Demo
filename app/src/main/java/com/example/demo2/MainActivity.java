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
    private Button   loginButton;
    private EditText userName;
    private EditText passWord;

    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.btnLogin);
        userName = findViewById(R.id.txtUserName);
        passWord = findViewById(R.id.txtPassword);

        final String strUserName = userName.getText().toString().trim();
        final String strPassword = passWord.getText().toString().trim();

        alertBuilder = new AlertDialog.Builder(MainActivity.this);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!strUserName.isEmpty() && !strPassword.isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this, OneRecipePage.class);
                    startActivity(intent);

                    // TODO
                    // Set UserName & Password fields to Blank.
                }
                else
                {
                    alertBuilder.setMessage("Invalid UserName or Password")
                                 .setPositiveButton("Yes", new DialogInterface.OnClickListener()
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
    }
}
