package com.example.demo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBRecipesModel
{
    private int mRecipeId;
    private int mUserId;
    private boolean mCategory;
    private String mRecipeName;
    private String mRecipeDescription;
    private ArrayList<String> mIngredientsList = new ArrayList<String>();

    public DBRecipesModel()
    {

    }

    public DBRecipesModel(int recipeId, int userId, boolean category, String recipeName,
                          String recipeDescription, ArrayList<String> ingredientsList)
    {
        this.mRecipeId = recipeId;
        this.mUserId = userId;
        this.mCategory = category;
        this.mRecipeName = recipeName;
        this.mRecipeDescription = recipeDescription;
        this.mIngredientsList = ingredientsList;
    }

    public Map<String, Object> toRecipeMap()
    {
        HashMap<String, Object> recipeValues = new HashMap<>();

        recipeValues.put("recipeId", getRecipeId());
        recipeValues.put("userId", getUserId());
        recipeValues.put("category", getCategory());
        recipeValues.put("rName", getRecipeName());
        recipeValues.put("rDescription", getRecipeDescription());
        recipeValues.put("ingredients", getIngredientsList());

        return recipeValues;
    }

    public void setRecipeId(int id) { this.mRecipeId = id; }
    private int getRecipeId() { return this.mRecipeId; }

    public void setUserId(int id) { this.mUserId = id; }
    private int getUserId() { return this.mUserId; }

    public void setCategory(boolean category) { this.mCategory = category; }
    private boolean getCategory() { return this.mCategory; }

    public void setRecipeName(String recipeName) { this.mRecipeName = recipeName; }
    private String getRecipeName() { return this.mRecipeName; }

    public void setRecipeDescription(String recipeDescription) { this.mRecipeDescription = recipeDescription; }
    private String getRecipeDescription() { return this.mRecipeDescription; }

    public void setIngredientsList(ArrayList<String> ingredientsList) { this.mIngredientsList = ingredientsList; }
    private ArrayList<String> getIngredientsList() { return this.mIngredientsList; }
}
