package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AccountRecoveryActivity extends AppCompatActivity
{
    private EditText userEmail;
    private TextView emailDNExist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recovery);

        userEmail    = (EditText) findViewById(R.id.userEmailRA);
        emailDNExist = (TextView) findViewById(R.id.tvEmailDNExist);
    }

    public void recoverAccount(View v)
    {
        if (!validateEmail())
        {
            return;
        }
        if (verifyEmailExist())
        {
            Intent intent = new Intent(AccountRecoveryActivity.this, VerifyCodeActivity.class);
            startActivity(intent);
        }
        else
        {
            emailDNExist.setVisibility(View.VISIBLE);
        }
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

    private boolean verifyEmailExist()
    {
        return true;
    }
}
