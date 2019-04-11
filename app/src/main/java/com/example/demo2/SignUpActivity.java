package com.example.demo2;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity
{
    private static final Pattern PASSWORD_PATTERN =
                         Pattern.compile("^" +        //Start of Expression
                                 "(?=.*[0-9])" +      //at least 1 digit
                                 "(?=.*[a-z])" +      //at least 1 lower case letter
                                 "(?=.*[A-Z])" +      //at least 1 upper case letter
                                 "(?=.*[@#$%^&+=])" + //at least 1 special character
                                 "(?=\\S+$)" +        //no white spaces
                                 ".{6,}" +            //at least 6 characters
                                 "$");                //End of Expression

    private TextInputLayout fullName;
    private TextInputLayout userEmail;
    private TextInputLayout passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName  = (TextInputLayout ) findViewById(R.id.txtSUName);
        userEmail = (TextInputLayout ) findViewById(R.id.txtSUEmail);
        passWord  = (TextInputLayout ) findViewById(R.id.txtSUPassword);
    }

    public void validateNameEmailPassword(View v)
    {
        if (!validName() || !validEmail() || !validPassword())
        {
            return;
        }

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean validName()
    {
        String nameInput = fullName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty())
        {
            fullName.setError("Field can't be empty");
            return false;
        }
        else if (nameInput.length() > 20)
        {
            fullName.setError("Name too long");
            return false;
        }
        else
        {
            fullName.setError(null);
            return true;
        }
    }

    private boolean validEmail()
    {
        String emailInput = userEmail.getEditText().getText().toString().trim();

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

    private boolean validPassword()
    {
        String passwordInput = passWord.getEditText().getText().toString().trim();

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
}