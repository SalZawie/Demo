package com.example.demo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBRecipesModel
{
    private String mImgURL;
    private boolean mCategory;
    private String mRecipeDescription;
    private ArrayList<String> mIngredientsList = new ArrayList<>();

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

    private boolean getCategory() { return this.mCategory; }

    private String getImgURL() { return this.mImgURL; }

    private String getRecipeDescription() { return this.mRecipeDescription; }

    private ArrayList<String> getIngredientsList() { return this.mIngredientsList; }
}
