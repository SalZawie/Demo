package com.example.demo2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

    private TextInputLayout userEmail;
    private TextInputLayout passWord;
    private FirebaseAuth user_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = (TextInputLayout) findViewById(R.id.txtEmail);
        passWord  = (TextInputLayout) findViewById(R.id.txtPassword);
        user_auth = FirebaseAuth.getInstance();
    }

    public void validateEmailPassword(View v)
    {
        if (!validateEmail() || !validatePassword())
        {
            return;
        }
        final String email = userEmail.getEditText().getText().toString();
        final String password = passWord.getEditText().getText().toString();
        //If fields are NOT EMPTY:
        user_auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //If Login is successful check if email is verified:
                    if(user_auth.getCurrentUser().isEmailVerified()){
                        Intent intent = new Intent(MainActivity.this, SearchPageActivity.class);
                        startActivity(intent);
                    }
                    else{
                        //TODO give error if email is not verifed
                    }

                }
            }
        });
    }

    private boolean validateEmail()
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

    private boolean validatePassword()
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
