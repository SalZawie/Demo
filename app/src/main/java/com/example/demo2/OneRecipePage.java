package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OneRecipePage extends AppCompatActivity {

    //Firebase account:

    ImageView photo;
    TextView recipeName;
    TextView ingredients;
    TextView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_recipe_page);
        //Initializing variables
        photo = findViewById(R.id.recipePhoto);
        recipeName = findViewById(R.id.recipeName);
        ingredients = findViewById(R.id.IngrList);
        instructions = findViewById(R.id.instructions);
    }

    public void back(View view)
    {
        Intent intent = new Intent(OneRecipePage.this, RecipePageActivity.class);
        startActivity(intent);
    }

    public void logout(View view)
    {
        Intent intent = new Intent(OneRecipePage.this, MainActivity.class);
        startActivity(intent);
    }

    public void add(View view)
    {
        Intent intent = new Intent(OneRecipePage.this, AddNewRecipeActivity.class);
        startActivity(intent);
    }
}
