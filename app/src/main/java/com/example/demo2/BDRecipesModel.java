package com.example.demo2;

import android.widget.EditText;
import java.util.ArrayList;

public class BDRecipesModel
{
    private String mRecipeName;
    private String mRecipeDescription;
    private ArrayList<EditText> mIngredientsList;

    public BDRecipesModel(String recipeName, String recipeDescription, ArrayList<EditText> ingredientsList)
    {
        this.mRecipeName = recipeName;
        this.mRecipeDescription = recipeDescription;
        this.mIngredientsList = ingredientsList;
    }

    public void setRecipeName(String recipeName) { this.mRecipeName = recipeName; }
    public String getRecipeName() { return this.mRecipeName; }

    public void setRecipeDescription(String recipeDescription) { this.mRecipeDescription = recipeDescription; }
    public String getRecipeDescription() { return this.mRecipeDescription; }

    public void setIngredientsList(ArrayList<EditText> ingredientsList) { this.mIngredientsList = ingredientsList; }
    public ArrayList<EditText> getIngredientsList() { return this.mIngredientsList; }
}
