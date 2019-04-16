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


public class AddNewRecipeActivity extends AppCompatActivity
{
    private LinearLayout mLinearLayout;

    private EditText mRecipeName;
    private EditText mRecipeDirections;

    private RadioButton mFoodButton;
    private RadioButton mDrinkButton;

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
        mFoodButton = findViewById(R.id.radio_btn_food);
        mDrinkButton = findViewById(R.id.radio_btn_drink);

        // Instantiate Buttons
        mAddIngredientBtn = findViewById(R.id.btn_add_an_ingredient);
        mBackBtn = findViewById(R.id.btn_previous_from_add_new_recipe);

        // Access to the activities control Class.
        addRecipeController = new AddRecipeController(mLinearLayout, mRecipeName, mRecipeDirections, mFoodButton);
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

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("recipeName", mRecipeName.getText().toString());
        savedInstanceState.putString("recipeDirections", mRecipeDirections.getText().toString());
        savedInstanceState.putBoolean("foodSelected", mFoodButton.isChecked());
        savedInstanceState.putBoolean("drinkSelected", mDrinkButton.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setRecipeData()
    {
        //new UrlPopUp().show(getSupportFragmentManager(), "UrlPopUp");
    }

    public void onSaveData(View view)
    {
        addRecipeController.DbPush();
    }

    public void onDelete(View v)
    {
        mLinearLayout.removeView((View) v.getParent());

        editTextCounter = editTextCounter > 0 ? editTextCounter++ : 0;
    }
}
