package com.example.demo2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyCodeActivity extends SignUpActivity
{
    // VC stands for verification code

    private ImageButton imgButtonVC;
    private EditText    emailVC;
    private FirebaseAuth user_auth   ;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        imgButtonVC = (ImageButton) findViewById(R.id.imgBtnVerifyCode);
        emailVC      = (EditText)    findViewById(R.id.etVerifyCode);
        user_auth = FirebaseAuth.getInstance();
    }

    public void resendVC(View view){
        current_user = user_auth.getCurrentUser();
        current_user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(VerifyCodeActivity.this, "A Verification Code was sent to : " + current_user.getEmail(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(VerifyCodeActivity.this, "Sending a Verification Code to : " + current_user.getEmail() + " has Failed !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
