package com.example.demo2;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddNewRecipeActivity extends AppCompatActivity
{
    private EditText mRecipeName;
    private EditText mEnterRecipe;
    private EditText mIngredient1;
    private EditText mIngredientList[];

    private Button mAddIngredientBtn;
    private Button mBackBtn;
    private Button mClearAllBtn;
    private Button mSaveRecipeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        mRecipeName = findViewById(R.id.edittxt_recipe_name);
        mEnterRecipe = findViewById(R.id.edittxt_recipe_description);

        // Instantiate Buttons
        mAddIngredientBtn = findViewById(R.id.btn_add_an_ingredient);
        mBackBtn = findViewById(R.id.btn_previous_from_add_new_recipe);
        mClearAllBtn = findViewById(R.id.btn_clear_all_input);
        mSaveRecipeBtn = findViewById(R.id.btn_save_recipe);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAddIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 2019-03-29 Add functionality to set new edit text field and it position.

            }
        });
    }

    private EditText createNewEditText()
    {
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText newEditText = new EditText(AddNewRecipeActivity.this);
        newEditText.setLayoutParams(layoutParams);

        return newEditText;
    }
}
