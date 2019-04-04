package com.example.demo2;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SearchPageActivity extends AppCompatActivity {
    private EditText searchOne;
    private EditText searchTwo;
    private EditText searchThree;

    private CheckBox foodCheckBox;
    private CheckBox drinkCheckBox;

    private String firstIngredient;
    private String secondIngredient;
    private String thirdIngredient;

    private Button backButton;
    private Button logoutButton;
    private Button addRecipeButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        searchOne = findViewById(R.id.searchOne);
        searchTwo = findViewById(R.id.searchTwo);
        searchThree = findViewById(R.id.searchThree);
        searchButton = findViewById(R.id.searchButton);
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        addRecipeButton = findViewById(R.id.addRecipeButton);
        foodCheckBox = findViewById(R.id.foodCheckBox);
        drinkCheckBox = findViewById(R.id.drinkCheckBox);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstIngredient = searchOne.getText().toString().toLowerCase();
                secondIngredient = searchTwo.getText().toString().toLowerCase();
                thirdIngredient = searchThree.getText().toString().toLowerCase();

                Intent intent = new Intent(SearchPageActivity.this, RecipePageActivity.class);
                intent.putExtra("Ingredients", new String[]{firstIngredient, secondIngredient, thirdIngredient});
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPageActivity.this, AddNewRecipeActivity.class);
                startActivity(intent);
            }
        });
    }
}
