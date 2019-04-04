package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipePageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backButton;
    private Button logoutButton;
    private Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        addRecipeButton = findViewById(R.id.addRecipeButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePageActivity.this, MainActivity.class); //(SearchPageActivity) Replace MainActivity.class with OneRecipePage.class
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePageActivity.this, AddNewRecipeActivity.class); //Replace MainAcitivity.class with AddNewRecipeActivity.class
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view)
    {

    }
}
