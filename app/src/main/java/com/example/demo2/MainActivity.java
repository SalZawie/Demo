package com.example.demo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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

public class MainActivity extends LoginMenu
{
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

        final String email    = userEmail.getEditText().getText().toString().toLowerCase();
        final String password = passWord.getEditText().getText().toString() ;

        user_auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    //If Login is successful check if email is verified:
                    if(user_auth.getCurrentUser().isEmailVerified())
                    {
                        Intent intent = new Intent(MainActivity.this, SearchPageActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Email has not been verified?");
                        builder.setTitle("Alert !");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setMessage("Invalid Email/Password");
                    builder.setTitle("Alert !");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            return;
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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