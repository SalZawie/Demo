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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewRecipeActivity extends AppCompatActivity
{
    private final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    private LinearLayout mLinearLayout;

    private EditText mRecipeName;
    private EditText mRecipeDirections;

    private RadioButton mFoodButton;

    private int editTextCounter = 1;
    private static final int MAX_EDITTEXT_LIMIT = 20;

    private Button mAddIngredientBtn;
    private Button mBackBtn;

    static AddRecipeController addRecipeController;

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

        // Instantiate Buttons
        mAddIngredientBtn = findViewById(R.id.btn_add_an_ingredient);
        mBackBtn = findViewById(R.id.btn_previous_from_add_new_recipe);
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
                addRecipeController.addNewEditTextField(mLinearLayout, AddNewRecipeActivity.this);
                editTextCounter++;
            }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewRecipeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setRecipeData()
    {
        addRecipeController.setRecipeName(mRecipeName);
        addRecipeController.setRecipeDescription(mRecipeDirections);
        addRecipeController.setIsFoodSelected(mFoodButton);
    }

    public void onSaveData(View view)
    {
        // TODO: 2019-04-09 Add a handler to this method.

        ArrayList<String> ingredientsList;
        ingredientsList = AddRecipeController.getAllIngredients(mLinearLayout);

        setRecipeData();

        if(addRecipeController.getRecipeName() != null && addRecipeController.getRecipeDirections() != null) {
            // Sets path
            DatabaseReference mDataBaseRef = mDatabase.getReference("recipes/" + "userID_Here");

            // Generate dynamic recipe ID
            String key = mDataBaseRef.push().getKey();
            // Sets data to the database model
            DBRecipesModel post = new DBRecipesModel(addRecipeController.getIsFoodSelected(), "imgURLPlaceholder",
                    addRecipeController.getRecipeDirections(), ingredientsList);

            Map<String, Object> postValues = post.toRecipeMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + addRecipeController.getRecipeName() + "/" + key, postValues);

            mDataBaseRef.updateChildren(childUpdates);
        }
        else
        {
            // TODO: 2019-04-11 add a toast. 
        }

        // TODO: 2019-04-09 need to add code to check for successful upload
        Toast.makeText(AddNewRecipeActivity.this, "Recipe Has Been Saved", Toast.LENGTH_SHORT).show();

    }

    public void onDelete(View v)
    {
        mLinearLayout.removeView((View) v.getParent());

        editTextCounter = editTextCounter > 0 ? editTextCounter++ : 0;
    }
}
