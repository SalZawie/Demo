package com.example.demo2;

public class Recipe {

    private String id;
    private String name;
    private String steps;
    private String userID;
    private String ingredients;
    private boolean category;
    private String imageURL;

    public Recipe(String id, String name, String steps, String userID, String ingredients, boolean category, String imgURL)
   {
        this.id = id;
        this.name = name;
        this.steps = steps;
        this.userID = userID;
        this.ingredients = ingredients;
        this.category = category;
        this.imageURL = imageURL;
   }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSteps() {
        return steps;
    }

    public String getUserID() {
        return userID;
    }

    public String getIngredients() {
        return ingredients;
    }

    public boolean isCategory() {
        return category;
    }

    public String getImageURL() {
        return imageURL;
    }

}
