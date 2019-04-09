package com.example.demo2;

import java.util.ArrayList;

public class BDRecipesModel
{
    private int mRecipeId;
    private String mRecipeName;
    private String mRecipeDescription;
    private ArrayList<String> mIngredientsList = new ArrayList<String>();

    public BDRecipesModel()
    {

    }

    public BDRecipesModel(int id, String recipeName, String recipeDescription, ArrayList<String> ingredientsList)
    {
        this.mRecipeId = id;
        this.mRecipeName = recipeName;
        this.mRecipeDescription = recipeDescription;
        this.mIngredientsList = ingredientsList;
    }

    public void setRecipeId(int id) { this.mRecipeId = id; }
    public int getRecipeId() { return this.mRecipeId; }

    public void setRecipeName(String recipeName) { this.mRecipeName = recipeName; }
    public String getRecipeName() { return this.mRecipeName; }

    public void setRecipeDescription(String recipeDescription) { this.mRecipeDescription = recipeDescription; }
    public String getRecipeDescription() { return this.mRecipeDescription; }

    public void setIngredientsList(ArrayList<String> ingredientsList) { this.mIngredientsList = ingredientsList; }
    public ArrayList<String> getIngredientsList() { return this.mIngredientsList; }
}
