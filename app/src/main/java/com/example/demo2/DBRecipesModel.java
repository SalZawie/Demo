package com.example.demo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBRecipesModel
{
    private String mImgURL;
    private boolean mCategory;
    private String mRecipeDescription;
    private ArrayList<String> mIngredientsList = new ArrayList<String>();

    public DBRecipesModel()
    {

    }

    public DBRecipesModel(boolean category, String imgURL, String recipeDescription, ArrayList<String> ingredientsList)
    {
        this.mCategory = category;
        this.mImgURL = imgURL;
        this.mRecipeDescription = recipeDescription;
        this.mIngredientsList = ingredientsList;
    }

    public Map<String, Object> toRecipeMap()
    {
        HashMap<String, Object> recipeValues = new HashMap<>();

        recipeValues.put("category", getCategory());
        recipeValues.put("imageURL", getImgURL());
        recipeValues.put("steps", getRecipeDescription());
        recipeValues.put("ingredients", getIngredientsList());

        return recipeValues;
    }

    private void setCategory(boolean category) { this.mCategory = category; }
    private boolean getCategory() { return this.mCategory; }

    private void setImgURL(String imgURL) { this.mImgURL = imgURL; }
    private String getImgURL() { return this.mImgURL; }

    private void setRecipeDescription(String recipeDescription) { this.mRecipeDescription = recipeDescription; }
    private String getRecipeDescription() { return this.mRecipeDescription; }

    private void setIngredientsList(ArrayList<String> ingredientsList) { this.mIngredientsList = ingredientsList; }
    private ArrayList<String> getIngredientsList() { return this.mIngredientsList; }
}
