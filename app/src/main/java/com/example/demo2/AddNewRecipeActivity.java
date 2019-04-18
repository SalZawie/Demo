package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

/* Author: Dan St-Jean, dan.stj@outlook.com */

public class AddNewRecipeActivity extends AppCompatActivity
{
    private LinearLayout mLinearLayout;

    private EditText mRecipeName;
    private EditText mRecipeDirections;
    private EditText mImageUrl;

    private RadioButton mFoodButton;

    private int editTextCounter = 1;
    private static final int MAX_EDITTEXT_LIMIT = 20;

    private Button mAddIngredientBtn;
    private Button mBackBtn;

    AddRecipeController addRecipeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        addRecipeController = new AddRecipeController();

        mLinearLayout = findViewById(R.id.parent_linear_layout);

        // UI elements
        mRecipeName = findViewById(R.id.edittxt_recipe_name);
        mRecipeDirections = findViewById(R.id.edittxt_recipe_description);
        mImageUrl = findViewById(R.id.edittxt_add_image_url);

        mFoodButton = findViewById(R.id.radio_btn_food);

        // Instantiate Buttons
        mAddIngredientBtn = findViewById(R.id.btn_add_an_ingredient);
        mBackBtn = findViewById(R.id.btn_previous_from_add_new_recipe);

        // Access to the activities control Class.
        addRecipeController = new AddRecipeController(mLinearLayout, mRecipeName,mRecipeDirections, mImageUrl, mFoodButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAddIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(editTextCounter >= MAX_EDITTEXT_LIMIT)
            {
                Toast toast = Toast.makeText(AddNewRecipeActivity.this, "You have reached max ingredients", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else
            {
                addRecipeController.addNewEditTextField(AddNewRecipeActivity.this);
                editTextCounter++;
            }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewRecipeActivity.this, SearchPageActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onSaveData(View view)
    {
        int returnCode = addRecipeController.DbPush();

        switch(returnCode) {
            case 1: {
                // Successfully saved recipe to the database
                Toast.makeText(AddNewRecipeActivity.this, "Recipe Has Been Saved", Toast.LENGTH_LONG).show();

                break;
            }
            case 2: {
                Toast.makeText(AddNewRecipeActivity.this, "There was a problem connecting to the Database",
                        Toast.LENGTH_LONG).show();

                break;
            }
            case 3: {
                Toast.makeText(AddNewRecipeActivity.this, "Recipe Name and Direction can't be empty \n" +
                        "You must enter at least 3 ingredient", Toast.LENGTH_LONG).show();

                break;
            }
            default:
                break;
        }
    }


    public void onDelete(View v)
    {
        mLinearLayout.removeView((View) v.getParent());

        editTextCounter = editTextCounter > 0 ? editTextCounter++ : 0;
    }
}
