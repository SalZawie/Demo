package com.example.demo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    private static final Pattern PASSWORD_PATTERN =
                         Pattern.compile("^" +                //Start of Expression
                                         "(?=.*[0-9])" +      //at least 1 digit
                                         "(?=.*[a-z])" +      //at least 1 lower case letter
                                         "(?=.*[A-Z])" +      //at least 1 upper case letter
                                         "(?=.*[@#$%^&+=])" + //at least 1 special character
                                         "(?=\\S+$)" +        //no white spaces
                                         ".{6,}" +            //at least 6 characters
                                         "$");                //End of Expression

    private EditText userEmail;
    private EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = (EditText) findViewById(R.id.txtEmail);
        passWord  = (EditText) findViewById(R.id.txtPassword);
    }

    public void validateEmailPassword(View v)
    {
        if (!validateEmail() || !validatePassword())
        {
            return;
        }

        Intent intent = new Intent(MainActivity.this, SearchPageActivity.class);
        startActivity(intent);
    }

    private boolean validateEmail()
    {
        String emailInput = userEmail.getText().toString().trim();

        if (emailInput.isEmpty())
        {
            userEmail.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            userEmail.setError("Please enter a valid email address");
            return false;
        }
        else
        {
            userEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword()
    {
        String passwordInput = passWord.getText().toString().trim();

        if (passwordInput.isEmpty())
        {
            passWord.setError("Field can't be empty");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches())
        {
            passWord.setError("Invalid password format .. \n At least 1 digit" +
                                                         "\n At least 1 lower case letter"  +
                                                         "\n At least 1 upper case letter"  +
                                                         "\n At least 1 special character " +
                                                         "\n ( @ # $ % ^ & + = )"   +
                                                         "\n At least 6 characters" +
                                                         "\n Spaces are not allowed");
            return false;
        }
        else
        {
            passWord.setError(null);
            return true;
        }
    }

    public void forgotMyPassword(View view)
    {
        Intent intent = new Intent(MainActivity.this, AccountRecoveryActivity.class);
        startActivity(intent);
    }

    public void signUp(View view)
    {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
