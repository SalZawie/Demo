package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        ingredients = findViewById(R.id.ingredients);
        instructions = findViewById(R.id.instructions);
        //Getting parameters from RecipePageAcivity
        Intent intent = getIntent();
        final String strRecipeName = intent.getStringExtra("recipeName");
        final String strIngredients = intent.getStringExtra("ingredients");
        final String strSteps = intent.getStringExtra("steps");
        final String strImageURL = intent.getStringExtra("imageURL");
        //Display the recipe;
        displayRecipe(strRecipeName,strIngredients,strSteps,strImageURL);
    }

    public void displayRecipe(String name, String ingr, String steps, String imgURL){
        //Clean the ingredients string
        ingr = ingr.substring(1, ingr.length()-1);
        recipeName.setText(name);
        ingredients.setText(ingr);
        instructions.setText(steps);
        Picasso.get().load(imgURL).into(photo);
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
