package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DrinksPage extends AppCompatActivity {

    private Button backButton;
    private Button logoutButton;
    private Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_drinks_page);

        Intent intent = getIntent();
        final String[] ingredients = intent.getStringArrayExtra("Ingredients");

        final Set ingredientsSet = new HashSet(Arrays.asList(ingredients));


        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        addRecipeButton = findViewById(R.id.addRecipeButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinksPage.this, MainActivity.class); //Replace MainActivity.class with OneRecipePage.class
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinksPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinksPage.this, AddNewRecipeActivity.class); //Replace MainAcitivity.class with AddNewRecipeActivity.class
                startActivity(intent);
            }
        });
    }
    }



