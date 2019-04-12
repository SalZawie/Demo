package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class SearchPageActivity extends AppCompatActivity {
    private EditText searchOne;
    private EditText searchTwo;
    private EditText searchThree;
    private CheckBox myRecipeCheckBox;
    private RadioButton foodRadioButtom;
    private RadioButton drinkRadioButtom;

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
        foodRadioButtom = findViewById(R.id.foodRadioButtom);
        drinkRadioButtom = findViewById(R.id.drinkRadioButtom);
        myRecipeCheckBox = findViewById(R.id.myRecipeChechBox);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstIngredient = searchOne.getText().toString().toLowerCase();
                secondIngredient = searchTwo.getText().toString().toLowerCase();
                thirdIngredient = searchThree.getText().toString().toLowerCase();

                if (foodRadioButtom.isChecked()) {
                    Intent intent = new Intent(SearchPageActivity.this, RecipePageActivity.class);
                    intent.putExtra("Ingredients", new String[]{firstIngredient, secondIngredient, thirdIngredient});
                    startActivity(intent);
                }
                else if(drinkRadioButtom.isChecked()) {
                    Intent intent = new Intent (SearchPageActivity.this, OneRecipePage.class);
                    intent.putExtra("Ingredients", new String[]{firstIngredient, secondIngredient, thirdIngredient});
                    startActivity(intent);
                }

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
