package com.example.demo2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountRecoveryActivity extends AppCompatActivity
{
    private EditText userEmail;
    private TextView emailDNExist;
    private Button btnRecover;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recovery);

        userEmail  = findViewById(R.id.userEmailRA);
        emailDNExist = findViewById(R.id.tvEmailDNExist);
        btnRecover = findViewById(R.id.btnRecoverAccount);
        auth = FirebaseAuth.getInstance();
    }

    public void recoverAccount(View v)
    {
        if (!validateEmail())
        {
            Toast.makeText(getApplicationContext(), "Input Email Error", Toast.LENGTH_SHORT).show();
        }
        else{
            if (verifyEmailExist())
            {
                resetPassword();
                Intent intent = new Intent(AccountRecoveryActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                emailDNExist.setVisibility(View.VISIBLE);
            }
        }

    }

    private boolean validateEmail()
    {
        String emailInput = userEmail.getText().toString().trim();
        boolean validated = false;

        if (emailInput.isEmpty())
        {
            userEmail.setError("Field can't be empty");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            userEmail.setError("Please enter a valid email address");
        }
        else
        {
            validated = true;
        }
        return validated;
    };

    public boolean verifyEmailExist(){
        return true;
    }

    public void resetPassword(){
        final String email = userEmail.getText().toString();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     Toast.makeText(getApplicationContext(), "Link to resend your password was sent to " + email, Toast.LENGTH_SHORT).show();
                     userEmail.setText("");
                 }
                 else{
                     Toast.makeText(getApplicationContext(), "Failed to send a link to " + email, Toast.LENGTH_SHORT).show();
                 }
             }
        });
    }
}
