package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class VerifyCodeActivity extends AppCompatActivity
{
    private ImageButton imgButtonVC;
    private EditText    etCode;
    private TextView    tvCodeNotMatching;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        imgButtonVC = (ImageButton) findViewById(R.id.imgBtnVerifyCode);
        etCode      = (EditText)    findViewById(R.id.etVerifyCode);

        tvCodeNotMatching = (TextView) findViewById(R.id.tvWrongCode);
    }

    public void verifyCode(View view)
    {
        if (etCode.getText().toString().trim().isEmpty() || !etCode.getText().toString().trim().equals("12345"))
        {
            tvCodeNotMatching.setVisibility(View.VISIBLE);
            return;
        }
        else
        {
            Intent intent = new Intent(VerifyCodeActivity.this, MainActivity.class); // Change MainActivity Page to the proper Activity
            startActivity(intent);
        }
    }
}
