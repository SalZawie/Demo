package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OneRecipePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_recipe_page);
    }

    public void back()
    {

    }

    public void logout()
    {
        Intent intent = new Intent(OneRecipePage.this, MainActivity.class);
        startActivity(intent);
    }

    public void add()
    {

    }
}
